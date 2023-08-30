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
            // input file is defaulted to English, but you can enter other languages if you wish.
            File VAGWAD = new File("VAGWAD.ENG");
            int length = (int)VAGWAD.length();
            FileInputStream in = new FileInputStream(VAGWAD);
            while(length>0){
                // check for VAG file - these four bytes are always at the start of the header.
                // for jak 2 the endianness changed - reverse the order of the four numbers below.
                if(in.read()==86 && in.read()==65 && in.read()==71 && in.read()==112){
                    // files are output as 1.VAG, 2.VAG, etc.
                    FileOutputStream out= new FileOutputStream(new File(Integer.toString(i)+".VAG"),true);
                    // create a 48 byte buffer for the header
                    byte[] a = new byte[48];
                    // bytes 1 - 4 are just the VAG header that we already read. Because we already read those four bytes, we now enter them manually into the buffer
                    a[0]=86;
                    a[1]=65;
                    a[2]=71;
                    a[3]=112;
                    // transfer the remainder of the header to the buffer. the header is always 48 bytes long in total.
                    for(int k=4;k<48;k++){
                        a[k] = (byte)in.read();
                    }
                    // detect the length of the current VAG file after the header, which is stored in bytes 13 - 16 of the header.
                    int j= (a[12]<<24)&0xff000000|
                           (a[13]<<16)&0x00ff0000|
                           (a[14]<< 8)&0x0000ff00|
                           (a[15]<< 0)&0x000000ff;
                    // write the header to the split file
                    out.write(a);
                    System.out.println(Integer.toString(j));
                    // write the remainder of the file
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
