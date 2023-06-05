import java.util.Scanner;

public class A1_121090661 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in); 
        String polynomial = s.nextLine(); 
        String monomial = "";
        String result = "";

        if (polynomial.charAt(0) != '-' && polynomial.charAt(0) != '+'){   //add '+' if necessary
            polynomial = '+' + polynomial;
        }
        for(int i=0;i < polynomial.length();i++) {
            if ((polynomial.charAt(i) == '+' || polynomial.charAt(i) == '-') && i != 0){   //divide when '+'/'-' appears
                result += finderivation(monomial);   //combine results
                monomial = "" + polynomial.charAt(i);   
            }
            else if(polynomial.charAt(i) != ' '){  //ignore spaces
                monomial += polynomial.charAt(i);
            }
        }
        result += finderivation(monomial);  //add the last result
        if (result.length() == 0){  //if there is only constant, then output '0'
            System.out.println(0);
        }
        else if (result.charAt(0) == '+'){  //ignore the first '+'
            result = result.substring(1,result.length());
            System.out.println(result);
        }
        else{System.out.println(result);
        }
    }

    public static String finderivation(String line){
        String derivation = "";
        int exponent1 = 0;
        int constant1 = 1;
        double constant2 = 1.0;
        double exponent2 = 0.0;
        boolean flag = true;
        int indexOfPoint = line.indexOf('.');
        int newConstant = 1;
        int newExponent = 0;
        int indexTime = line.indexOf('*');
        int indexExp = line.indexOf('^');
        if (line.indexOf('x') == -1){   //only constant
                derivation = "";
                flag = false;
            }
        if (indexTime != -1){  //'*' exists
            constant1 = Integer.parseInt(line.substring(1,indexTime));   //ready for integer exponent
            constant2 = Integer.parseInt(line.substring(1,indexTime));   //ready for float exponent
            }
        if (indexExp == -1){   //'*'  doesn't exist
            exponent1 = 1;
            newConstant = constant1*exponent1;
            newExponent = exponent1-1;
            }
            
        else{
                if (indexOfPoint != -1){  //exponent is float
                    exponent2 = Double.valueOf(line.substring(indexExp+1));
                    double newConstant2 = (Double)(constant2*exponent2);
                    double newExponent2 = exponent2-1;
                    derivation = line.charAt(0) + Double.toString(newConstant2) + "*x^" + Double.toString(newExponent2);
                    return derivation;
                    }
                
                else{exponent1 = Integer.parseInt(line.substring(indexExp+1));  //exponent is integer
                    newConstant = (int)constant1*exponent1;
                    newExponent = (int)exponent1-1;
                }    
            }
            
        if (flag){  //'x' exists
                if (newExponent == 0){   //new exponent is 0, so we only use the new constant
                    derivation = line.charAt(0) + Integer.toString(newConstant);
                }
                else if (newConstant == 1){   //new constanr is 1, so we only use 'x' and its exponent
                    derivation = line.charAt(0) + "x^" + Integer.toString(newExponent);
                }
                else if(newExponent == 1){   //new exponent is 1, so we do not need to show '^1'
                    derivation = line.charAt(0) + Integer.toString(newConstant) + "*x";
                }
                else{
                    derivation = line.charAt(0) + Integer.toString(newConstant) + "*x^" + Integer.toString(newExponent);
                }
            }
        return derivation;
    }
}