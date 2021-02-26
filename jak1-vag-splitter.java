import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class jak1-vag-splitter
{
    public static void main(String[] args){
        try{
            int i=0;
            File VAGWAD = new File("VAGWAD.ENG");
            int length = (int)VAGWAD.length();
            FileInputStream in = new FileInputStream(VAGWAD);
            while(length>0){
                if(in.read()==86 && in.read()==65 && in.read()==71 && in.read()==112){
                    FileOutputStream out= new FileOutputStream(new File(Integer.toString(i)+".VAG"),true);
                    byte[] a = new byte[48];
                    a[0]=86;
                    a[1]=65;
                    a[2]=71;
                    a[3]=112;
                    for(int k=4;k<48;k++){
                        a[k] = (byte)in.read();
                    }
                    int j= (a[12]<<24)&0xff000000|
                           (a[13]<<16)&0x00ff0000|
                           (a[14]<< 8)&0x0000ff00|
                           (a[15]<< 0)&0x000000ff;
                    out.write(a);
                    System.out.println(Integer.toString(j));
                    byte[] b = new byte[j];
                    for(int k=0;k<j;k++){
                        b[k] = (byte)in.read();
                    }
                    out.write(b);
                    out.close();
                    i++;
                }
            }
        }
        catch(IOException e){
            System.out.println("IOException");
        }
    }
}
