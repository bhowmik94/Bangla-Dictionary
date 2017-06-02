package dictionary;

public class RootWord {
    String rootword = "";
    String text = "";
    String refereneceWord = "";
    int length;
    public RootWord(String tx, int l){
        text = tx;
        length = l;
    }
    String rootWordFinding(){
        if(text.contains("[")){
            rootword=text.substring(0,text.indexOf("["));
            if(text.contains(Character.toString((char)0x21d2)) && (text.indexOf(Character.toString((char)0x21d2))-rootword.length()<=rootword.length()+5)){
                refereneceWord=text.substring(text.indexOf(Character.toString((char)0x21d2))+1,length);
                if(refereneceWord.length()<5*rootword.length())
                    refereneceWord="";
            }
        }
        else if(text.contains(Character.toString((char)0x21d2))){
            rootword=text.substring(0,text.indexOf(Character.toString((char)0x21d2)));
            if(text.indexOf(Character.toString((char)0x21d2))-rootword.length()<=rootword.length()+5){
                refereneceWord=text.substring(text.indexOf(Character.toString((char)0x21d2))+1,length);
                if(refereneceWord.length()>5*rootword.length())
                    refereneceWord="";
            }
        }
        else if(text.contains("=")){
            rootword=text.substring(0,text.indexOf("="));
            if(text.indexOf("=")-rootword.length()<=rootword.length()+5){
                refereneceWord=text.substring(text.indexOf("=")+1,length);
                if(refereneceWord.length()>5*rootword.length())
                    refereneceWord="";
            }
        }
        else if(text.contains("(")){
            rootword=text.substring(0,text.indexOf("("));
        }
        else if(text.contains("{")){
            rootword=text.substring(0,text.indexOf("{"));
        }
        else if(text.contains("  ")){
            rootword=text.substring(0,text.indexOf("  "));
            if(text.indexOf("  ")-rootword.length()<=rootword.length()+5){
                refereneceWord=text.substring(text.indexOf("  ")+1,length);
                if(refereneceWord.length()>5*rootword.length())
                    refereneceWord="";
            }
        }
        else if(text.contains(" ")){
            rootword=text.substring(0,text.indexOf(" "));
            if(text.indexOf(" ")-rootword.length()<=rootword.length()+5){
                refereneceWord=text.substring(text.indexOf(" ")+1,length);
                if(refereneceWord.length()>5*rootword.length())
                    refereneceWord="";
            }
        }
        
        rootword=rootword.trim();
        rootword=rootword.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
        rootword=rootword.replaceAll(Character.toString((char)0x09e8),"");//for bengali two
        rootword=rootword.replaceAll(Character.toString((char)0x09e9),"");//for bengali three
        rootword=rootword.replaceAll(Character.toString((char)0x09ea),"");//for bengali four
        rootword=rootword.replaceAll(Character.toString((char)0x09eb),"");//for bengali five
        rootword=rootword.replaceAll(Character.toString((char)0x09ec),"");//for bengali six
        rootword=rootword.replaceAll(Character.toString((char)0x09ed),"");//for bengali seven
        
        rootword=rootword.replaceAll(Character.toString((char)0x223c),"");//a special case(~)
        //rootword=rootword.replaceAll(Character.toString((char)0x2013),"");//bengli hypen(-)
        rootword=rootword.trim();
        return rootword;
    }
    String[] rootWordSeparator(){
        String[] tx = rootword.split(",");
        return tx;
    }
    
    String refereneceWordFinder(){
        refereneceWord = refereneceWord.trim();
        return refereneceWord;
    }
    String[] refereneceWordSeparator(){
        String[] tx = refereneceWord.split(",");
        return tx;
    }
}
