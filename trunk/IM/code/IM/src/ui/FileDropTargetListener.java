package ui;

import java.awt.dnd.DropTargetAdapter;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import java.io.File;


/**
 * �ļ��Ϸż���
 */
public class  FileDropTargetListener extends DropTargetAdapter {
	 public void drop(DropTargetDropEvent event) { 
            event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);   
            //��ȡ�Ϸŵ�����   
            Transferable transferable = event.getTransferable();   
            DataFlavor[] flavors = transferable.getTransferDataFlavors();   
            //�����Ϸ���������������ݸ�ʽ   
            for (int i = 0; i < flavors.length; ++i ) {     
                DataFlavor d = flavors[i];   
                try  {   
                   //����Ϸ����ݵ����ݸ�ʽ���ļ��б�   
                   if (d.equals(DataFlavor.javaFileListFlavor)) {   
                        //ȡ���ϷŲ�������ļ��б�   
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
                //ǿ���ϷŲ���������ֹͣ�����Ϸ�Դ   
                event.dropComplete(true);   
            }   
        }   

}
