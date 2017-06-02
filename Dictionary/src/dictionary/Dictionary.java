package dictionary;
/**
 *
 * @author SUJAN
*/
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Dictionary {

    public static void main(String[] args) throws IOException {
        int i=0,j=0,k=0,length;
        int rtCount=0,meanCount=0,pronCount=0,referenceCount=0,posCount=0;
        int rtTag=0,meanTag=0,pronTag=0,referenceTag=0,posTag=0;
        String rootWord = "";
        String pronunciation="";
        String pos="";
        String meaning="";
        String refWord="";
        String test="";
        String inputDirectory = "D:\\in\\";
        String outputDirectory = "D:\\outputDirectory\\";
        String[] paths;
        File first = null;
        File second = null;
        FileInputStream input = null;
        FileOutputStream output = null;
        int count = 0,start=0;
        String check = "";
        try{
            
            first = new File(inputDirectory);//file directory
            paths = first.list();//finds the paths in the given directory
            
            //Classes for handing xml file
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Document doc = docBuilder.newDocument();
            StreamResult result = new StreamResult(new File("D:\\outputFile.xml"));//output file
            DOMSource source = null;
            Element rootElement = doc.createElement("dictionary");//creating root element or tag
            doc.appendChild(rootElement);
            
            for(String path:paths){
                second = new File(inputDirectory+path);
                if(second.isFile()&&second.getPath().endsWith(".docx")){//determine whether it ends with .docx extension
                    ++count;//tracks the file number
                    input = new FileInputStream(second.getPath());
                    //output = new FileOutputStream(outputDirectory+"output"+count+".docx");
                    XWPFDocument document1 = new XWPFDocument(input);//document creation from the .docx file
                    //XWPFDocument document2= new XWPFDocument();
                    List<XWPFParagraph> paragraphs = document1.getParagraphs();//#paragraphs in the document
                    //System.out.println(paragraphs.size());
                    XWPFParagraph pr = null;
                    XWPFRun run2 = null;
                    //XWPFParagraph paragraph = document2.createParagraph();
                    /*for chunk2
                    if(count==9)
                        start = 1;
                    else
                        start = 2;
                    */
                    //for first as well as all chunks
                    if(count==1)
                        start=21;
                    else if(count==6 || count==7 || count==9)
                        start=1;
                    else if(count==24)
                        start=3;
                    else
                        start=2;
                    //for chunk3
                    //start=2;
                    /*for chunk4
                    if(count==24)
                        start = 3;
                    else
                        start = 2;
                    */
                    //Reading the whole text paragraph by paragraph
                    for(int n =start; n < paragraphs.size(); n++){
                        
                        pr = document1.getParagraphs().get(n);
                        test = pr.getText();//coping the whole paragraph text to this string
                        test = test.trim();
                        length = test.length();//length of the paragraph text
                        /*
                        if(count==1&&n==123)
                            check=test;
                        */   
                        //for chunk1
                        if(count==5)
                            if(n>=108 && n<=133)//unnecessary paragraphs with a lot of rule breaks
                                continue;
                        
                        if(!test.isEmpty()&&test!=""){
                            
                            //------Root Word detection----------
                            RootWord rw = new RootWord(test,test.length());
                            rootWord = rw.rootWordFinding();
                            //----------Pronunciation Detection----------
                            Pronunciation prn = new Pronunciation(test,length);
                            pronunciation = prn.pronunciationFinding();
                           //----------Parts of Speech-----------------
                            PoS parts = new PoS(test,length);
                            pos = parts.posFinding();
                            //----------Meaning------------
                            Meaning mn = new Meaning(test,length);
                            meaning = mn.meaningFinding();
                            
                        //------Writing XML processes-----------

                            Element item = doc.createElement("item");//a set of all elements parsed from a paragraph
                            rootElement.appendChild(item);
                            //-------Root Word-----
                            if(rootWord!=""&&!rootWord.isEmpty()){
                                ++rtTag;
                                Element word = doc.createElement("word");
                                String[] multiRoot = rw.rootWordSeparator();
                                String[] tempRoot;
                                if(multiRoot.length==1){
                                    multiRoot[0]=multiRoot[0].trim();
                                    tempRoot = multiRoot[0].split(" ");
                                    word.appendChild(doc.createTextNode(tempRoot[0]));
                                    System.out.println(count+"Root Word"+n+": "+tempRoot[0]);
                                    ++rtCount;
                                }
                                else if(multiRoot.length>1){
                                    for(String tmp:multiRoot){
                                        tmp = tmp.trim();
                                        //tempRoot = tmp.split(" ");
                                        if(tmp.isEmpty()==false){
                                            Element value = doc.createElement("value");
                                            value.appendChild(doc.createTextNode(tmp));
                                            word.appendChild(value);
                                            System.out.println("Root Word"+n+": "+tmp);
                                            ++rtCount;
                                        }
                                    }
                                }
                                item.appendChild(word);
                            }
                            //------Reference--------
                            refWord = rw.refereneceWordFinder();
                            if(refWord!=""&&!refWord.isEmpty()){
                                ++referenceTag;
                                Element reference = doc.createElement("reference");
                                String[] multiReference = rw.refereneceWordSeparator();
                                if(multiReference.length==1){
                                    multiReference[0]=multiReference[0].trim();
                                    reference.appendChild(doc.createTextNode(multiReference[0]));
                                    ++referenceCount;
                                }
                                else if(multiReference.length>1){
                                    for(String tmp:multiReference){
                                        tmp = tmp.trim();
                                        if(tmp.isEmpty()==false){
                                            Element value = doc.createElement("value");
                                            value.appendChild(doc.createTextNode(tmp));
                                            reference.appendChild(value);
                                            ++referenceCount;
                                        }
                                    }
                                }
                                item.appendChild(reference);
                            }
                            //-------Pronunciation-----------
                            if(pronunciation!=""&&!pronunciation.isEmpty()){
                                ++pronTag;
                                Element pronunciation1 = doc.createElement("pronunciation");
                                String[] multiPronunciation = prn.pronunciationSeparator();
                                if(count==1&&n==21)
                                    pronunciation1.appendChild(doc.createTextNode(pronunciation));
                                
                                if(multiPronunciation.length==1){
                                    multiPronunciation[0]=multiPronunciation[0].trim();
                                    pronunciation1.appendChild(doc.createTextNode(multiPronunciation[0]));
                                    ++pronCount;
                                }
                                else if(multiPronunciation.length>1){
                                    for(String tmp:multiPronunciation){
                                        tmp = tmp.trim();
                                        if(tmp.isEmpty()==false){
                                            Element value = doc.createElement("value");
                                            value.appendChild(doc.createTextNode(tmp));
                                            pronunciation1.appendChild(value);
                                            ++pronCount;
                                        }
                                    }
                                }
                                item.appendChild(pronunciation1);
                                
                            }
                            //------Parts of Speech------
                            if(pos!=""&&!pos.isEmpty()&&pos.length()>1){
                                ++posTag;
                                Element pos1 = doc.createElement("pos");
                                pos1.appendChild(doc.createTextNode(pos));
                                item.appendChild(pos1);
                                ++posCount;
                            }
                            //------Meaning--------
                            if(meaning!=""&&!meaning.isEmpty()){
                                ++meanTag;
                                Element meaning1 = doc.createElement("meaning");
                                String[] multiMeaning = mn.meaningSeparator();
                                if(multiMeaning.length==1){
                                    multiMeaning[0]=multiMeaning[0].trim();
                                    meaning1.appendChild(doc.createTextNode(multiMeaning[0]));
                                    ++meanCount;
                                }
                                else if(multiMeaning.length>1){
                                    for(String tmp:multiMeaning){
                                        tmp = tmp.trim();
                                        if(tmp.isEmpty()==false){
                                            Element value = doc.createElement("value");
                                            value.appendChild(doc.createTextNode(tmp));
                                            meaning1.appendChild(value);
                                            ++meanCount;
                                        }
                                    }
                                }
                                item.appendChild(meaning1);
                            }
                        }
                        //----set null for a new paragraph------
                        rootWord = "";
                        pronunciation = "";
                        pos = "";
                        meaning = "";
                    }
                    input.close();
                }
            }
            source = new DOMSource(doc);
            transformer.transform(source, result);
        }catch (Exception e) {
                e.printStackTrace();
        }
        /*
        for(i=0; i<check.length(); i++){
            System.out.println(check.charAt(i)+": "+Integer.toHexString(check.codePointAt(i)));
        }                   
        */ 
        System.out.println("Rootword Count: "+rtCount+"\tRootWord Tag Total: "+rtTag);
        System.out.println("ReferenceCount: "+referenceCount+"\tReference Tag Total: "+referenceTag);
        System.out.println("PronunciationCount: "+pronCount+"\tPronunciation Tag Total: "+pronTag);
        System.out.println("PosCount: "+posCount+"\tPos Tag Total: "+posTag);
        System.out.println("MeaningCount: "+meanCount+"\tMeaning Tag Total: "+meanTag);
        
    }
}


//----------Root Word Finding------------
    /*
    for(XWPFRun run : pr.getRuns()){
        if(run.isBold()){
            rootWord += run.text();
        }
    }
    */

                /* last
                for(i=0;i<length;i++){
                    if(test.charAt(i)==']'){
                        String[] str = test.split("]");
                        
                        if(str.length <2)
                            break;
                        else{
                            
                            String interested = str[1];
                            //temp=str[2];
                            String[] str1 = interested.split(" ");
                            interested = str1[1];
                            partsOfSpeech=interested;
                            
                            
                            for(k = 2; k < str1.length; k++){
                                temp+=str1[k]+" ";
                            }
                            
                            break;
                        }
                    }
                }
            
                
                //System.out.println("PoS: "+partsOfSpeech);
                
                ///meaning
                
                for(i=0;i<temp.length();i++){
                    //if(test.codePointAt(i)==0x09E7){
                    if(temp.codePointAt(i)==0x964)
                        break;
                    else
                        meaning+=temp.charAt(i);
                    /*    
                    for(j = i+1;test.codePointAt(j)!=0x0964;j++){
                           meaning+=test.charAt(j); 
                        }
                        break;
                    //}
                    */
                //} last */ 
                //System.out.println("Meaning: "+meaning);  
                
                //text = "";
                //System.out.println();
                
                //apatoto comment out
                
                
             
                //meaning="";
                
                
                /*
                System.out.println(test);
                for (XWPFRun run : pr.getRuns()){
                    i++;
                    System.out.println("Run"+i+": "+run.text());
                }*/







        //putting the run text in the document2 paragraph 
             /* run2 =  paragraph.createRun();
                run2.setText(rootWord);
                run2.addBreak();
            
                System.out.print(rootWord);
                System.out.print("\t");
                //if(n%10==1)
                //    System.out.println();
                rootWord = "";
            
            
            
            //testing section
            /*
            String text="";
            for(int n=21;n<paragraphs.size();n++){
                int i=0,j=0,length;
                pr = document1.getParagraphs().get(n);
                String test = pr.getText();
                length = test.length();
                //System.out.println("Paragraph Length: "+length);
                
                for(j=0;j<length;j++){
                    //System.out.println("CharAt"+j+": "+test.charAt(j));
                    if(test.charAt(j)=='['){
                        //System.out.println(test.charAt(j));
                        for(int k = j+1; test.charAt(k)!=']';k++){
                           text+=test.charAt(k); 
                        }
                        break;
                        
                    }
                        
                }
                System.out.println(text);
                text = "";
                /*
                System.out.println(test);
                for (XWPFRun run : pr.getRuns()){
                    i++;
                    System.out.println("Run"+i+": "+run.text());
                }*/
            //}*/
            





            /*
            //System.out.println("Paragraph51: "+tx);
            for(int i=0;i<tx.length();i++){
                String strHexNumber = Integer.toHexString(tx.codePointAt(i));
                System.out.println("character: "+tx.charAt(i)+"\t"+strHexNumber);
                if(tx.codePointAt(i)==0x0964){
                    System.out.println("Position of Dari: "+(i+1));
                }
            }
            */








/*
                System.out.println("The paragraph at position 50 is:");
                System.out.println(document1.getParagraphs().get(50).getParagraphText());
                
                System.out.println("Length : "+document1.getParagraphs().get(50).getParagraphText().length());
                //System.out.println(document1.getParagraphs().get(50).getParagraphText().);
                //System.out.println(document1.getParagraphs().get(50).getParagraphText().trim());
                /*
                int pos=0;
                pr = document1.getParagraphs().get(50);
                document2.createParagraph();
                document2.setParagraph(pr,pos);
                document2.write(out);
                
                for(int n = 21; n < paragraphs.size(); n++){
                    pr = document1.getParagraphs().get(n);
                    document2.createParagraph();
                    pos = document2.getParagraphs().size()-1;
                    System.out.println(pos);
                    document2.setParagraph(pr,pos);
                }
                document2.write(out);
                
                /*
                XWPFParagraph para = document2.createParagraph();
                XWPFRun paraOne = para.createRun();
                paraOne.setBold(true);
                paraOne.setText("Font Style");
                paraOne.addBreak();
                document2.write(out);
                */
                /*
                List<XWPFParagraph> para = document2.getParagraphs();
                System.out.println(para.size());
                XWPFParagraph pr1 = null;
                pr1 = document2.getParagraphs().get(0);
                System.out.println(pr1.getText());
                //int pos = 0;
                String rootWord = null;
                for (XWPFRun run : pr1.getRuns()) {
                    System.out.println("Current run IsBold : " + run.isBold());
                    if(run.isBold()){
                        System.out.println(run.text());
                        rootWord = run.text();
                    }
                }
                System.out.println(rootWord);
                //out1.write(rootWord.getBytes());
                //document3.write(out1.);
*/
    
            /*  List<XWPFParagraph> paragraphs = document.getParagraphs();
                for(XWPFParagraph para : paragraphs){
                    System.out.println(para.getText());
                }
            */
            /*  int num = paragraphs.size();
                for(int n = 0; n < num; n++){
                    System.out.println(paragraphs.get(n).getText());
                }
            */