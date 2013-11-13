/**
 * 
 */
package com.example.socketclient;

import java.io.Serializable;

/**
 * @author Mateusz
 * Klasa przesyłana tak jak TCP_Data, ale tylko przy łączeniu
 */
public class Connect implements Serializable {
	/**
	 * Hasło do połączenia (6 cyfr)
	 */
public String haslo;
/**
 * Nazwa telefonu/kompa (może działać w dwie strony)
 */
public String nazwa;
}
