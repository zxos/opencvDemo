// Ireadface.aidl
package cn.csg.gjdl.robot.aidl;

import cn.csg.gjdl.robot.aidl.IreadfaceListener;
// Declare any non-default types here with import statements

interface Ireadface {


    void setListener(IreadfaceListener listener);

    void sendMsg(String msg);
}
