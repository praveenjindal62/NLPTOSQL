import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class QueryTranslator
{
static String convertedString="";
	QueryTranslator(String inputString, String dbName, String driver, String username, String password,boolean toggle) throws IOException//Constructor
	{
		//Query Preprocessing
		String preProcessedString=QueryPreProcessor.preProcessor(inputString,dbName,driver,username,password);
		TokenizerModule t=new TokenizerModule(preProcessedString);
		String correctedString=t.correctString();
		System.out.println(correctedString);
		
	

		//Query Translation
		ANTLRInputStream input=new ANTLRInputStream(correctedString.replace(" ","")+"\n");
		nlptosqlLexer lex=new nlptosqlLexer(input);
		CommonTokenStream tokens=new CommonTokenStream(lex);
		nlptosqlParser parser=new nlptosqlParser(tokens);
		ParseTree tree=parser.translate();
		convertedString=parser.result;
                if(toggle){
		ParserRuleContext tree1=(ParserRuleContext)tree;
        tree1.inspect(parser);
                }
		System.out.println(convertedString);
	
		
		//Query Postprocessing 
		Pattern pattern = Pattern.compile("value[0-9]");	
		for (int i=0;i<QueryPreProcessor.items;i++)
		{
			System.out.println(i+" "+QueryPreProcessor.ReplacedContent[i][0]+" "+QueryPreProcessor.ReplacedContent[i][1]);
			Matcher matcher = pattern.matcher(QueryPreProcessor.ReplacedContent[i][1]);
			if(matcher.find())
			{
                            String s1=DateUtil.convertToDate(QueryPreProcessor.ReplacedContent[i][0]);
                            if(s1!=null)
                            {
                                convertedString=convertedString.replace(QueryPreProcessor.ReplacedContent[i][1],"'"+s1+"'");
                            }
                            else{
				int s=convertedString.indexOf(QueryPreProcessor.ReplacedContent[i][1]);
				if(convertedString.charAt(s-1)=='%')
				{
					if(convertedString.length()-s==7)
					{
						String k=QueryPreProcessor.ReplacedContent[i][0];
						System.out.println(k);
						k="'%"+k+"%'";
						convertedString=convertedString.replace("%"+QueryPreProcessor.ReplacedContent[i][1]+"%",k);
					}
					else
					{
						String k=QueryPreProcessor.ReplacedContent[i][0];
						k="'%"+k+"'";
						convertedString=convertedString.replace("%"+QueryPreProcessor.ReplacedContent[i][1],k);
					}
				}
				else 
				if(convertedString.length()-s==7)
					{
						String k=QueryPreProcessor.ReplacedContent[i][0];
						k="'"+k+"%'";
						convertedString=convertedString.replace(QueryPreProcessor.ReplacedContent[i][1]+"%",k);
					}
				else
				{
				convertedString=convertedString.replace(QueryPreProcessor.ReplacedContent[i][1],"'"+QueryPreProcessor.ReplacedContent[i][0]+"'");
				}
                            }
			}
			else
			convertedString=convertedString.replace(QueryPreProcessor.ReplacedContent[i][1],QueryPreProcessor.ReplacedContent[i][0]);
		}
		System.out.println(convertedString);
	}
	
	
	
	public static void main(String args[]) throws IOException
	{
	Scanner scan=new Scanner(System.in);
	String query=scan.nextLine();
	String dbName="classicmodels";
	String driver="com.mysql.jdbc.Driver";
	String username="root";
	String pass="";
	new QueryTranslator(query,dbName,driver,username,pass,true);
	}
}