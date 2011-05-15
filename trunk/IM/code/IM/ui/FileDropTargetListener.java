package ui;

import java.awt.dnd.DropTargetAdapter;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import java.io.File;


/**
 * 文件拖放监听
 */
public class  FileDropTargetListener extends DropTargetAdapter {
	 public void drop(DropTargetDropEvent event) { 
            event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);   
            //获取拖放的内容   
            Transferable transferable = event.getTransferable();   
            DataFlavor[] flavors = transferable.getTransferDataFlavors();   
            //遍历拖放内容里的所有数据格式   
            for (int i = 0; i < flavors.length; ++i ) {     
                DataFlavor d = flavors[i];   
                try  {   
                   //如果拖放内容的数据格式是文件列表   
                   if (d.equals(DataFlavor.javaFileListFlavor)) {   
                        //取出拖放操作里的文件列表   
                        java.util.List fileList  = (java.util.List) transferable.getTransferData(d);   
                        for (Object f : fileList) {
							if(f instanceof File ) {
                                File fo =(File)f;
								if (fo.isFile()) {
									MskFrame.getInstance().sendFileRequest(f.toString());
								} else {
                                    MskFrame.getInstance().sendFileFoldRequest(f.toString());
							    }
							}
                        }   
                    }   
                } catch (Exception e) {     
                    e.printStackTrace();   
                }   
                //强制拖放操作结束，停止阻塞拖放源   
                event.dropComplete(true);   
            }   
        }   

}
