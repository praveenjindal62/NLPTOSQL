import java.io.*;
import java.util.*;
class TokenizerModule {
    String[] Token;
    String[] CorrectedWord;
    Spelling spelling;
	static Scanner scan;
    TokenizerModule(String s) throws IOException
    {
        Token=s.split(" ");
        CorrectedWord=new String[Token.length];
		spelling =new Spelling("big.txt");
		String res=correctString();
        //System.out.println(res);
    }
	public String correctString()
	{
		String k="";
        for (int i=0;i< Token.length;i++)
		{
			CorrectedWord[i]=spelling.correct(Token[i]);
			k+=CorrectedWord[i]+" ";
		}
        return k;
	}
	public static void main(String arg[]) throws IOException
	{
		scan=new Scanner(System.in);
		String s= scan.nextLine();
		//System.out.println(s);
		new TokenizerModule(s);
	}
    
}
