import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @author 
* @author Daniela Batz 19214
*/
public class HashMap<k,v> implements WordSet{
	private HashMap<String,String > stringMap=new HashMap<String, String>();
    public boolean valor;
    
    public void add(Word objeto) {
    	stringMap.put(objeto.getWord(), objeto.getType());
    }
    
    public Word get(Word pala) {
    	stringMap.containsValue(pala.getWord());
    	if (valor==true){
    		Word word = new Word();
    		word.setWord(pala.getWord());
    		word.setType(stringMap.get(pala.getWord()));
    		return word;
    	}else {
    		return null;
    	}
    }
}