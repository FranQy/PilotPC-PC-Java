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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Hasło do połączenia (6 cyfr)
	 */
public String haslo;
/**
 * Nazwa telefonu/kompa (może działać w dwie strony)
 */
public String nazwa;
/**
 * Wersja aplikacji/serwera (może działać w dwie strony)
 */
public String wersja;

/**
 * informuje, czy kod został przyjęty
 * @author Mateusz
 *
 */
public enum Status {nieznanyBlad, zlyKod, kodZmieniony, ok };
public Status status;
}
