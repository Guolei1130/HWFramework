package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.lang.UProperty;
import java.io.IOException;
import java.text.CharacterIterator;

class ThaiBreakEngine extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = (byte) 3;
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = (byte) 2;
    private static final byte THAI_MIN_WORD_SPAN = (byte) 4;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = (byte) 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = (byte) 3;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fMarkSet;
    private static UnicodeSet fSuffixSet;
    private static UnicodeSet fThaiWordSet;
    private DictionaryMatcher fDictionary;

    static {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.icu.text.ThaiBreakEngine.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: jadx.core.utils.exceptions.DecodeException:  in method: android.icu.text.ThaiBreakEngine.<clinit>():void
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:46)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:98)
	... 5 more
Caused by: java.lang.IllegalArgumentException: bogus opcode: 00e9
	at com.android.dx.io.OpcodeInfo.get(OpcodeInfo.java:1197)
	at com.android.dx.io.OpcodeInfo.getFormat(OpcodeInfo.java:1212)
	at com.android.dx.io.instructions.DecodedInstruction.decode(DecodedInstruction.java:72)
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:43)
	... 6 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.icu.text.ThaiBreakEngine.<clinit>():void");
    }

    public ThaiBreakEngine() throws IOException {
        super(Integer.valueOf(1), Integer.valueOf(2));
        setCharacters(fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }

    public boolean equals(Object obj) {
        return obj instanceof ThaiBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean handles(int c, int breakType) {
        boolean z = true;
        if (breakType != 1 && breakType != 2) {
            return false;
        }
        if (UCharacter.getIntPropertyValue(c, UProperty.SCRIPT) != 38) {
            z = false;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DequeI foundBreaks) {
        if (rangeEnd - rangeStart < 4) {
            return 0;
        }
        int wordsFound = 0;
        PossibleWord[] words = new PossibleWord[3];
        for (int i = 0; i < 3; i++) {
            words[i] = new PossibleWord();
        }
        fIter.setIndex(rangeStart);
        while (true) {
            int current = fIter.getIndex();
            if (current >= rangeEnd) {
                break;
            }
            int uc;
            int wordLength = 0;
            int candidates = words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd);
            if (candidates == 1) {
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                wordsFound++;
            } else if (candidates > 1) {
                if (fIter.getIndex() < rangeEnd) {
                    while (true) {
                        if (words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                            words[wordsFound % 3].markCurrent();
                            if (fIter.getIndex() < rangeEnd) {
                                do {
                                    if (words[(wordsFound + 2) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                                        break;
                                    }
                                } while (words[(wordsFound + 1) % 3].backUp(fIter));
                                if (words[wordsFound % 3].backUp(fIter)) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (words[wordsFound % 3].backUp(fIter)) {
                            break;
                        }
                    }
                }
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                wordsFound++;
            }
            if (fIter.getIndex() < rangeEnd && wordLength < 3) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0 || (wordLength != 0 && words[wordsFound % 3].longestPrefix() >= 3)) {
                    fIter.setIndex(current + wordLength);
                } else {
                    int remaining = rangeEnd - (current + wordLength);
                    int pc = fIter.current();
                    int chars = 0;
                    while (true) {
                        fIter.next();
                        uc = fIter.current();
                        chars++;
                        remaining--;
                        if (remaining <= 0) {
                            break;
                        }
                        if (fEndWordSet.contains(pc)) {
                            if (fBeginWordSet.contains(uc)) {
                                int candidate = words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd);
                                fIter.setIndex((current + wordLength) + chars);
                                if (candidate > 0) {
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                        pc = uc;
                    }
                    if (wordLength <= 0) {
                        wordsFound++;
                    }
                    wordLength += chars;
                }
            }
            while (true) {
                int currPos = fIter.getIndex();
                if (currPos < rangeEnd && fMarkSet.contains(fIter.current())) {
                    fIter.next();
                    wordLength += fIter.getIndex() - currPos;
                }
            }
            if (fIter.getIndex() < rangeEnd && wordLength > 0) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0) {
                    UnicodeSet unicodeSet = fSuffixSet;
                    uc = fIter.current();
                    if (unicodeSet.contains(uc)) {
                        if (uc == 3631) {
                            if (fSuffixSet.contains(fIter.previous())) {
                                fIter.next();
                            } else {
                                fIter.next();
                                fIter.next();
                                wordLength++;
                                uc = fIter.current();
                            }
                        }
                        if (uc == 3654) {
                            if (fIter.previous() != '\u0e46') {
                                fIter.next();
                                fIter.next();
                                wordLength++;
                            } else {
                                fIter.next();
                            }
                        }
                    }
                }
                fIter.setIndex(current + wordLength);
            }
            if (wordLength > 0) {
                foundBreaks.push(Integer.valueOf(current + wordLength).intValue());
            }
        }
        if (foundBreaks.peek() >= rangeEnd) {
            foundBreaks.pop();
            wordsFound--;
        }
        return wordsFound;
    }
}
