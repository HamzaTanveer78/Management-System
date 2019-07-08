package classes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;


public class Validation {

	
	  

		public Boolean ValidateNumber(String txt) {
		        Boolean status=true;
			if(txt.contains("@")||txt.contains(".")
				
			    ||txt.contains("#")||txt.contains("$")||txt.contains("%")
	        	||txt.contains("^")||txt.contains("&")||txt.contains("*")
				||txt.contains("(")||txt.contains(")")||txt.contains("~")
				||txt.contains("`")||txt.contains("-")||txt.contains("_")
				||txt.contains("=")||txt.contains("+")||txt.contains("}")
				||txt.contains("{")||txt.contains("[")||txt.contains("]")
				||txt.contains("/")||txt.contains("?")||txt.contains(">")
				||txt.contains("<")||txt.contains(",")||txt.contains(":")
				||txt.contains(";")||txt.contains("'")||txt.contains("\"")
				||txt.contains("A")||txt.contains("B")||txt.contains("C")
				||txt.contains("D")||txt.contains("E")||txt.contains("F")
				||txt.contains("G")||txt.contains("H")||txt.contains("I")
				||txt.contains("J")||txt.contains("K")||txt.contains("L")
				||txt.contains("M")||txt.contains("N")||txt.contains("O")
				||txt.contains("P")||txt.contains("Q")||txt.contains("R")
				||txt.contains("S")||txt.contains("T")||txt.contains("U")
				||txt.contains("V")||txt.contains("W")||txt.contains("X")
				||txt.contains("Y")||txt.contains("Z")||txt.contains("a")
				||txt.contains("a")||txt.contains("b")||txt.contains("c")
				||txt.contains("d")||txt.contains("e")||txt.contains("f")
				||txt.contains("g")||txt.contains("h")||txt.contains("i")
				||txt.contains("j")||txt.contains("k")||txt.contains("l")
				||txt.contains("m")||txt.contains("n")||txt.contains("o")
				||txt.contains("p")||txt.contains("q")||txt.contains("r")
				||txt.contains("s")||txt.contains("t")||txt.contains("u")
				||txt.contains("v")||txt.contains("w")||txt.contains("x")
				||txt.contains("y")||txt.contains("z")
				
				) {
			
		
			status=false;
		}
		
			return status;

		}
		
		
		
		public Boolean ValidateName(String txt) {
			Boolean status=true;
			if(txt.contains("@")||txt.contains(".")
					||txt.contains("!")
					||txt.contains("#")||txt.contains("$")||txt.contains("%")
					||txt.contains("^")||txt.contains("&")||txt.contains("*")
					||txt.contains("(")||txt.contains(")")||txt.contains("~")
					||txt.contains("`")||txt.contains("-")||txt.contains("_")
					||txt.contains("=")||txt.contains("+")||txt.contains("}")
					||txt.contains("{")||txt.contains("[")||txt.contains("]")
					||txt.contains("/")||txt.contains("?")||txt.contains(">")
					||txt.contains("<")||txt.contains(",")||txt.contains(":")
					||txt.contains(";")||txt.contains("'")||txt.contains("\"")
					||txt.contains("1")||txt.contains("2")||txt.contains("3")
					||txt.contains("4")||txt.contains("5")||txt.contains("6")
					||txt.contains("7")||txt.contains("8")||txt.contains("9")
					||txt.contains("\\")||txt.contains("0")
					
					) {
				status=false;
				
				}
			
			return status;
		}
		
		public Boolean validateContact(String txt) {
			Boolean status=false;
			if(txt.contains("@")||txt.contains(".")
					||txt.contains("!")
					||txt.contains("#")||txt.contains("$")||txt.contains("%")
					||txt.contains("^")||txt.contains("&")||txt.contains("*")
					||txt.contains("(")||txt.contains(")")||txt.contains("~")
					||txt.contains("`")||txt.contains("-")||txt.contains("_")
					||txt.contains("=")||txt.contains("+")||txt.contains("}")
					||txt.contains("{")||txt.contains("[")||txt.contains("]")
					||txt.contains("/")||txt.contains("?")||txt.contains(">")
					||txt.contains("<")||txt.contains(",")||txt.contains(":")
					||txt.contains(";")||txt.contains("'")||txt.contains("\"")
					||txt.contains("A")||txt.contains("B")||txt.contains("C")
					||txt.contains("D")||txt.contains("E")||txt.contains("F")
					||txt.contains("G")||txt.contains("H")||txt.contains("I")
					||txt.contains("J")||txt.contains("K")||txt.contains("L")
					||txt.contains("M")||txt.contains("N")||txt.contains("O")
					||txt.contains("P")||txt.contains("Q")||txt.contains("R")
					||txt.contains("S")||txt.contains("T")||txt.contains("U")
					||txt.contains("V")||txt.contains("W")||txt.contains("X")
					||txt.contains("Y")||txt.contains("Z")||txt.contains("a")
					||txt.contains("a")||txt.contains("b")||txt.contains("c")
					||txt.contains("d")||txt.contains("e")||txt.contains("f")
					||txt.contains("g")||txt.contains("h")||txt.contains("i")
					||txt.contains("j")||txt.contains("k")||txt.contains("l")
					||txt.contains("m")||txt.contains("n")||txt.contains("o")
					||txt.contains("p")||txt.contains("q")||txt.contains("r")
					||txt.contains("s")||txt.contains("t")||txt.contains("u")
					||txt.contains("v")||txt.contains("w")||txt.contains("x")
					||txt.contains("y")||txt.contains("z")
					
					) {
				
			
				status=false;

				
			}
			else {
				
				status=true;
			}
			return status;
		}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
	
}
