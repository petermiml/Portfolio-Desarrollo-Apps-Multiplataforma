package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import vista.VentanaContrase�a;
import vista.VentanaPrincipal;

public class ManejadorEventos implements ActionListener{

	
	private VentanaPrincipal vp;
	double num1,num2,resultado;
	int resultadoInt;
	String resultadoFinal;
	
	public ManejadorEventos(VentanaPrincipal vp) {
		this.vp = vp;
	}
	
	public double suma(double num1, double num2) {
		return num1 + num2;
	}
	
	public double resta(double num1, double num2) {
		return num1 - num2;
	}
	
	public double multiplicacion(double num1, double num2) {
		return num1 * num2;
	}
	
	public double division(double num1, double num2) {
		return num1 / num2;
	}
	
	public double raiz3(double num1) {
		
		return Math.cbrt(num1);	
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if(e.getSource() == vp.getSumar()) {
				num1 = Double.valueOf(vp.getNum1().getText());
				num2 = Double.valueOf(vp.getNum2().getText());
				// Realizamos la operaci�n.
				double resultado = suma(num1,num2);							
				// Si el resultado obtenido entre 1 da como resto 0, hacemos un cast e imprimimos el n�mero como un entero, si no, como decimal.
				if(resultado%1 == 0) {										
					resultadoInt = (int) resultado;
					String resultadoFinal = String.valueOf(resultadoInt);
					vp.getResultado().setText(resultadoFinal);
				}else {
					String resultadoFinal = String.valueOf(suma(num1,num2));
					vp.getResultado().setText(resultadoFinal);
				}
			// 	Realizamos el mismo procedimiento para el resto de operaciones, con la salvedad de que en la resta y la divisi�n
			// 	redondeamos con dos decimales como m�ximo ya que, como te coment�, a veces el error tiene una imprecisi�n milim�trica y 
			//	esto es lo que nos recomendaste.
			
			
			}else if(e.getSource() == vp.getRestar()){
				num1 = Double.valueOf(vp.getNum1().getText());
				num2 = Double.valueOf(vp.getNum2().getText());
				resultado = resta(num1,num2);
				if(resultado%1 == 0) {										
					resultadoInt = (int) resultado;
					resultadoFinal = String.valueOf(resultadoInt);
					vp.getResultado().setText(resultadoFinal);
				}else {
					resultadoFinal = String.valueOf(Math.round(resta(num1,num2)*100d)/100d);	
					vp.getResultado().setText(resultadoFinal);
				}
				
			
			}else if(e.getSource() == vp.getMultiplicar()) {
				num1 = Double.valueOf(vp.getNum1().getText());
				num2 = Double.valueOf(vp.getNum2().getText());
				resultado = multiplicacion(num1,num2);							

				if(resultado%1 == 0) {										
					resultadoInt = (int) resultado;
					resultadoFinal = String.valueOf(resultadoInt);
					vp.getResultado().setText(resultadoFinal);
				}else {
					resultadoFinal = String.valueOf(multiplicacion(num1,num2));
					vp.getResultado().setText(resultadoFinal);
				}
				
			
			
			}else if(e.getSource() == vp.getDividir()) {
				num1 = Double.valueOf(vp.getNum1().getText());
				num2 = Double.valueOf(vp.getNum2().getText());
				resultado = division(num1,num2);							
				
				if(num2 == 0) {
					vp.getResultado().setText("No se puede dividir\n entre 0");
				}else if(resultado%1 == 0) {										
					resultadoInt = (int) resultado;
					resultadoFinal = String.valueOf(resultadoInt);
					vp.getResultado().setText(resultadoFinal);
				}else {
					resultadoFinal = String.valueOf(Math.round(division(num1,num2)*100d)/100d);	
					vp.getResultado().setText(resultadoFinal);
				}
			
			
			
			}else if(e.getSource() == vp.getRaiz3()) {
				
				if (!vp.getNum1().getText().equals("") && !vp.getNum2().getText().equals("")) {
					JOptionPane.showMessageDialog(null,"No es posible calcular la raiz c�bica de dos n�meros. Por favor, introduce el n�mero en el primer campo.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}else if(!vp.getNum2().getText().equals("")){
					JOptionPane.showMessageDialog(null,"Por favor, introduce el n�mero en el primer campo.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}else {
					num1 = Double.valueOf(vp.getNum1().getText());
					
					VentanaContrase�a ventana2 = new VentanaContrase�a(vp,true);
					if(ventana2.getConfirm()==true){
					    ventana2.setVisible(true);   
					}
					   						
				     if(raiz3(num1)%1 != 0 &&ventana2.getConfirm()==false) {
						resultado = raiz3(num1);
						resultadoInt = (int) resultado;
						resultadoFinal = String.valueOf(resultado);
						vp.getResultado().setText(resultadoFinal); 
						
					}else if(raiz3(num1)%1 == 0 &&ventana2.getConfirm()==false) {	
						resultado = raiz3(num1);
						resultadoInt = (int) resultado;
						resultadoFinal = String.valueOf(resultadoInt);
						vp.getResultado().setText(resultadoFinal); 
						
					}
				}
			
			}else if(e.getSource() == vp.getRaiz2()) {
				
				JOptionPane.showMessageDialog(null,"Funcionalidad no disponible", "Error", JOptionPane.INFORMATION_MESSAGE);	
		}
			
		//Aqu� recogemos la excepci�n de si el usuario introduce caracteres  que no sean n�meros incluido campo vac�o.
		}catch(NumberFormatException e1){	
			JOptionPane.showMessageDialog(null,"Dato introducido no v�lido o el campo est� vac�o.\n\nPara introducir decimales, recuerda utilizar el punto (.) en lugar de la coma (,).", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
