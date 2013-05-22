
package Svg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.avalon.framework.activity.Suspendable;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
 
/**
 * 
 * This class exports diagrams to another location 
 * 
 */
public class Exporter {

	public Exporter(){

	}//end of constructor

	/**
	 * Converts file into desired file and saves it
	 *
	 * @param inputfile the inputfile
	 * @param targetfile the targetfile
	 * @param option the option for file format
	 */
	public void Convert(String inputfile,String targetfile,String option){

		if(option.equals(".png")){
			// Create a JPEG transcoder
			PNGTranscoder t = new PNGTranscoder();

			// Set the transcoding hints.
			t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
					new Float(.8));

			File f = new File(inputfile);
			// Create the transcoder input.
			String svgURI =f.toURI().toString();
			System.out.println(svgURI);
			TranscoderInput input = new TranscoderInput(svgURI);

			// Create the transcoder output.
			OutputStream ostream=null;
			try {
				ostream = new FileOutputStream(targetfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			try {
				t.transcode(input, output);
			} catch (TranscoderException e1) {
				System.out.println("problem accured with transcoding");
			}

			// Flush and close the stream.
			try {
				ostream.flush();
				ostream.close();
			} catch (IOException e) {
				System.out.println("problem with closing application");
			}
		}
		else if(option.equals(".jpg")){
			// Create a JPEG transcoder
			JPEGTranscoder t = new JPEGTranscoder();
			// Set the transcoding hints.
			t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
					new Float(.8));
			// Create the transcoder input.
			String svgURI=null;
			try {
				svgURI = new File(inputfile).toURL().toString();
			} catch (MalformedURLException e) {
				System.out.println("problem with file");
			}
			TranscoderInput input = new TranscoderInput(svgURI);

			// Create the transcoder output.
			OutputStream ostream=null;
			try {
				ostream = new FileOutputStream(targetfile);
			} catch (FileNotFoundException e) {
				System.out.println("could not create output file");
			}
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			try {
				t.transcode(input, output);
			} catch (TranscoderException e) {
				System.out.println("problem with transcoder");
			}
			// Flush and close the stream.
			try {
				ostream.close();
				ostream.flush();
			} catch (IOException e) {
				System.out.println("problem accured with transcoding");
			}
		}

		else if(option.equals(".svg")){
			Path source = Paths.get(inputfile);
			Path target = Paths.get(targetfile);
			try {
			
				System.out.println("input file:"+inputfile);
				System.out.println("target file"+targetfile);
				Files.copy(source, target,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("could not export svg file");
			}
		}
	}//end of convert method
}
