grammar nlptosql;

@parser::members {
static String result="";
static String whereSubString="";
static String temp1,temp2,value,value1,value2,condField;
static int condType=0,likeType;



public static void Select_Column2_Table1(String c1,String c2, String t)
	{
		result="SELECT "+c1+","+c2+" FROM "+t;
		conditionMethod();
	}

public static void Select_Table1(String t)
	{
		result="SELECT * FROM "+t;
		conditionMethod();
	}


public static void Select_Column1_Table1(String c,String t)
	{
		result="SELECT "+c+" FROM "+t;
		conditionMethod();
	}

public static void conditionMethod()
	{
		if(condType!=0){
		whereSubString="WHERE ";
		switch(condType)
		{
			case 1: whereSubString+=condField+" > "+value;
					break;
			case 2: whereSubString+=condField+" < "+value;
					break;
			case 3: whereSubString+=condField+" BETWEEN "+value1+ " AND "+value2;
					break;
			case 4: whereSubString+=condField+" LIKE ";
					switch(likeType)
					{
						case 1: whereSubString+=value+"%";
								break;
						case 2: whereSubString+="%"+value;
								break;
						case 3:	whereSubString+="%"+value+"%";
								break;
					}
					
					break;
			case 5: whereSubString+=condField+" = "+value;
					break;
		}
	}
	result+=" "+whereSubString;
	}
}

translate: select;

select:(ask|question)+ Qualifier+? Connective? ('of'|'from')? Table (Ignore_words condition)? EOL {Select_Table1($Table.text);}
	|(ask|question) Qualifier? Field ('of'|'from') Qualifier? Table (Ignore_words condition)? EOL  {Select_Column1_Table1($Field.text,$Table.text);}
	|(ask|question) Qualifier? Connective? Table Qualifier? Field (Ignore_words condition)? EOL {Select_Column1_Table1($Field.text,$Table.text);}
	|(ask|question) Qualifier? Field{temp1=$Field.text;} ('and'|',') Field{temp2=$Field.text;} ('of'|'from') Qualifier? Table (Ignore_words condition)? EOL { Select_Column2_Table1(temp1,temp2,$Table.text);}
    |(ask|question) Qualifier? Connective? Table Qualifier? Field{temp1=$Field.text;} ('and'|',') Field{temp2=$Field.text;} (Ignore_words condition)? EOL  {Select_Column2_Table1(temp1,temp2,$Table.text);}
	
	
	
	
	
    |(ask|question) Ignore_words Table Ignore_words Table Ignore_words EOL  {System.out.println("Executed10");}
	|(ask|question) Qualifier? Connective? Table Connective Table EOL {System.out.println("Executed9");}
	;

		
Qualifier: 'of'|'the'|'every'('thing')?|'all'('the')?|'any'|'our'|'about'|'detail';
ask:('tell'|'show'|'list'|'display'|'select') (('to')?('me'|'us'))?;

Connective: 'our'|'of'|'and';

question: Whats|Whos;
Whats: 'what\'s'|'what\'re'|'what' Isare?;
Whos: 'who\'s'|'who\'re'|'who' Isare?;
Isare: 'is'|'are';
		
Table: 'table'INT;
Field: 'column'INT;
value:'value'INT{value=$value.text;};
INT:[0-9];


Ignore_words: 'suchthat'|'please'|'whose'|'with'|'where';


condition:greater_than {condType=1;}
		  |less_than {condType=2;}
		  |between {condType=3;}
		  |like {condType=4;}
		  |equal {condType=5;}
		  ;
equal: Field{condField=$Field.text;} Pre_equal value;
Pre_equal: 'by'|'of'|'for'|'to'|'from'|'with'|'is'|'are'|'equalto'|'isequalto'|'equivalentto'|'isequivalentto'|'issameas'|'sameas';
greater_than: Field{condField=$Field.text;} Pre_greater value ;
Pre_greater: ('is'|'are')? ('more'|'greater'|'higher'|'expensive'|'taller'|'bigger') ('than');
less_than: Field{condField=$Field.text;} Pre_less value ;
Pre_less: ('is'|'are')? ('less'|'lower'|'cheaper'|'shorter'|'smaller') ('than');
between:Field{condField=$Field.text;} Pre_between value{value1=value;} 'and' value{value2=value;};
Pre_between :('is'|'are')? 'between';
like :Field{condField=$Field.text;} 'starts' 'with' value{likeType=1;}
      |Field{condField=$Field.text;} ('ends' 'with'|'ended' 'in') value{likeType=2;}
      |Field{condField=$Field.text;} ('contain'|'contains') value{likeType=3;}
	   ;
   
	
/*
value:Delimiter Word Delimiter{value=$Word.text;};	
Word:([\t]*)?[a-zA-Z][a-zA-Z0-9]*;
Delimiter:'\'';

value: number|date|Word;
number:('$')?('-')?INT+('.')?INT*;
INT:[0-9];
Word:([\t]*)?[a-zA-Z][a-zA-Z0-9]*;

date: 'today'|'tommorow'|'yesterday'
	|day
	|('last'|'previous'|'next') date_period
	|date_format
	;
date_period: ('week'|'month')|month|day|('financial')?'year';
month:Long_month|Short_month;
Long_month:'january'|'february'|'march'|'april'|'may'|'june'|'july'|'august'|'september'|'october'|'november'|'december';
Short_month:'jan'|'feb'|'mar'|'apr'|'may'|'jun'|'jul'|'aug'|'sep'|'oct'|'nov'|'dec';
day: Long_day|Short_day;
Long_day:'sunday'|'monday'|'tuesday'|'wednesday'|'thursday'|'friday'|'saturday';
Short_day:'sun'|'mon'|'tue'|'wed'|'thu'|'fri'|'sat';
date_format: DD date_sep (MM|month) date_sep (YYYY|YY)
	|(MM|month) date_sep DD date_sep (YYYY|YY)
	| month DD '\,'? (YYYY|YY)
	|(YYYY|YY) date_sep (MM|month) date_sep DD
	| DD date_sep (MM|month)
	| (MM|month) date_sep DD
	| month DD
	;
date_sep:'-'|'\/'|'\\';
DD:[0-9][0-9]?;
MM:[0-9][0-9]?;
YYYY:[0-9][0-9][0-9][0-9];
YY:[0-9][0-9];

*/

EOL:'\r'? '\n';
