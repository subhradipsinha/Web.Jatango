/**
 * Created By Subhradip Sinha
 * Date: 10/1/2024
 * Project Name: jatango
 */

package demo.pageObject;

import org.openqa.selenium.io.Zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

public class ZipCodes {
    public static void createdZip(String filepath )throws Throwable{
        String zip= Zip.zip(new File(filepath));
        BufferedOutputStream stream= new BufferedOutputStream(new FileOutputStream(filepath+".zip"),10000);
        byte[] decode=Base64.getDecoder().decode(zip);
        stream.write(decode);
        stream.close();
    }

    public static void createdZipFolder(String filepath2)throws Throwable{
        String zip= Zip.zip(new File(filepath2));
        BufferedOutputStream stream= new BufferedOutputStream(new FileOutputStream(filepath2+".zip"),10000);
        byte[] decode=Base64.getDecoder().decode(zip);
        stream.write(decode);
        stream.close();
    }
    public static void createdZipPDFFolder(String filepath3)throws Throwable{
        String zip= Zip.zip(new File(filepath3));
        BufferedOutputStream stream= new BufferedOutputStream(new FileOutputStream(filepath3+".zip"),10000);
        byte[] decode=Base64.getDecoder().decode(zip);
        stream.write(decode);
        stream.close();
    }

    public static void createdZipHTMLFolder(String filepath4)throws Throwable{
        String zip= Zip.zip(new File(filepath4));
        BufferedOutputStream stream= new BufferedOutputStream(new FileOutputStream(filepath4+".zip"),10000);
        byte[] decode=Base64.getDecoder().decode(zip);
        stream.write(decode);
        stream.close();
    }
}
