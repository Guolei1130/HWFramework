package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkStatsService extends IInterface {

    public static abstract class Stub extends Binder implements INetworkStatsService {
        private static final String DESCRIPTOR = "android.net.INetworkStatsService";
        static final int TRANSACTION_advisePersistThreshold = 10;
        static final int TRANSACTION_forceUpdate = 9;
        static final int TRANSACTION_forceUpdateIfaces = 8;
        static final int TRANSACTION_getDataLayerSnapshotForUid = 4;
        static final int TRANSACTION_getMobileIfaces = 5;
        static final int TRANSACTION_getNetworkTotalBytes = 3;
        static final int TRANSACTION_incrementOperationCount = 6;
        static final int TRANSACTION_openSession = 1;
        static final int TRANSACTION_openSessionForUsageStats = 2;
        static final int TRANSACTION_registerUsageCallback = 11;
        static final int TRANSACTION_setUidForeground = 7;
        static final int TRANSACTION_unregisterUsageRequest = 12;

        private static class Proxy implements INetworkStatsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public INetworkStatsSession openSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_openSession, _data, _reply, 0);
                    _reply.readException();
                    INetworkStatsSession _result = android.net.INetworkStatsSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INetworkStatsSession openSessionForUsageStats(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(Stub.TRANSACTION_openSessionForUsageStats, _data, _reply, 0);
                    _reply.readException();
                    INetworkStatsSession _result = android.net.INetworkStatsSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getNetworkTotalBytes(NetworkTemplate template, long start, long end) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(Stub.TRANSACTION_openSession);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(start);
                    _data.writeLong(end);
                    this.mRemote.transact(Stub.TRANSACTION_getNetworkTotalBytes, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getDataLayerSnapshotForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    NetworkStats networkStats;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(Stub.TRANSACTION_getDataLayerSnapshotForUid, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        networkStats = (NetworkStats) NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        networkStats = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return networkStats;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getMobileIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getMobileIfaces, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void incrementOperationCount(int uid, int tag, int operationCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(tag);
                    _data.writeInt(operationCount);
                    this.mRemote.transact(Stub.TRANSACTION_incrementOperationCount, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUidForeground(int uid, boolean uidForeground) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (uidForeground) {
                        i = Stub.TRANSACTION_openSession;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setUidForeground, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceUpdateIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_forceUpdateIfaces, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_forceUpdate, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void advisePersistThreshold(long thresholdBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(thresholdBytes);
                    this.mRemote.transact(Stub.TRANSACTION_advisePersistThreshold, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DataUsageRequest registerUsageCallback(String callingPackage, DataUsageRequest request, Messenger messenger, IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    DataUsageRequest dataUsageRequest;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (request != null) {
                        _data.writeInt(Stub.TRANSACTION_openSession);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (messenger != null) {
                        _data.writeInt(Stub.TRANSACTION_openSession);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(Stub.TRANSACTION_registerUsageCallback, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        dataUsageRequest = (DataUsageRequest) DataUsageRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        dataUsageRequest = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return dataUsageRequest;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterUsageRequest(DataUsageRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(Stub.TRANSACTION_openSession);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_unregisterUsageRequest, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkStatsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkStatsService)) {
                return new Proxy(obj);
            }
            return (INetworkStatsService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            INetworkStatsSession _result;
            switch (code) {
                case TRANSACTION_openSession /*1*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = openSession();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case TRANSACTION_openSessionForUsageStats /*2*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = openSessionForUsageStats(data.readString());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case TRANSACTION_getNetworkTotalBytes /*3*/:
                    NetworkTemplate networkTemplate;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        networkTemplate = (NetworkTemplate) NetworkTemplate.CREATOR.createFromParcel(data);
                    } else {
                        networkTemplate = null;
                    }
                    long _result2 = getNetworkTotalBytes(networkTemplate, data.readLong(), data.readLong());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case TRANSACTION_getDataLayerSnapshotForUid /*4*/:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkStats _result3 = getDataLayerSnapshotForUid(data.readInt());
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(TRANSACTION_openSession);
                        _result3.writeToParcel(reply, TRANSACTION_openSession);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TRANSACTION_getMobileIfaces /*5*/:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result4 = getMobileIfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result4);
                    return true;
                case TRANSACTION_incrementOperationCount /*6*/:
                    data.enforceInterface(DESCRIPTOR);
                    incrementOperationCount(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setUidForeground /*7*/:
                    data.enforceInterface(DESCRIPTOR);
                    setUidForeground(data.readInt(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_forceUpdateIfaces /*8*/:
                    data.enforceInterface(DESCRIPTOR);
                    forceUpdateIfaces();
                    reply.writeNoException();
                    return true;
                case TRANSACTION_forceUpdate /*9*/:
                    data.enforceInterface(DESCRIPTOR);
                    forceUpdate();
                    reply.writeNoException();
                    return true;
                case TRANSACTION_advisePersistThreshold /*10*/:
                    data.enforceInterface(DESCRIPTOR);
                    advisePersistThreshold(data.readLong());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_registerUsageCallback /*11*/:
                    DataUsageRequest dataUsageRequest;
                    Messenger messenger;
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        dataUsageRequest = (DataUsageRequest) DataUsageRequest.CREATOR.createFromParcel(data);
                    } else {
                        dataUsageRequest = null;
                    }
                    if (data.readInt() != 0) {
                        messenger = (Messenger) Messenger.CREATOR.createFromParcel(data);
                    } else {
                        messenger = null;
                    }
                    DataUsageRequest _result5 = registerUsageCallback(_arg0, dataUsageRequest, messenger, data.readStrongBinder());
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(TRANSACTION_openSession);
                        _result5.writeToParcel(reply, TRANSACTION_openSession);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TRANSACTION_unregisterUsageRequest /*12*/:
                    DataUsageRequest dataUsageRequest2;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        dataUsageRequest2 = (DataUsageRequest) DataUsageRequest.CREATOR.createFromParcel(data);
                    } else {
                        dataUsageRequest2 = null;
                    }
                    unregisterUsageRequest(dataUsageRequest2);
                    reply.writeNoException();
                    return true;
                case IBinder.INTERFACE_TRANSACTION /*1598968902*/:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void advisePersistThreshold(long j) throws RemoteException;

    void forceUpdate() throws RemoteException;

    void forceUpdateIfaces() throws RemoteException;

    NetworkStats getDataLayerSnapshotForUid(int i) throws RemoteException;

    String[] getMobileIfaces() throws RemoteException;

    long getNetworkTotalBytes(NetworkTemplate networkTemplate, long j, long j2) throws RemoteException;

    void incrementOperationCount(int i, int i2, int i3) throws RemoteException;

    INetworkStatsSession openSession() throws RemoteException;

    INetworkStatsSession openSessionForUsageStats(String str) throws RemoteException;

    DataUsageRequest registerUsageCallback(String str, DataUsageRequest dataUsageRequest, Messenger messenger, IBinder iBinder) throws RemoteException;

    void setUidForeground(int i, boolean z) throws RemoteException;

    void unregisterUsageRequest(DataUsageRequest dataUsageRequest) throws RemoteException;
}
