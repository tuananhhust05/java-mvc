package com.epay.ewallet.service.post.utils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	@SuppressWarnings("unused")
	public static boolean isNumberInt(String num) {
		try {
			int i = Integer.parseInt(num);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 *@param String[] stock, String finding_value
	 *@return true if stock contains finding_value else return @false
	 */
//	public static boolean contains(String[] stock, String valueToFind) {
//		if (ArrayUtils.contains(stock, valueToFind)) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * @param String str
	 * @return boolean true if the str is null or length = 0
	 *
	 */
	public static boolean isStrNull(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean strContainsSpace(String str) {
		if (str.contains(" ")) {
			return true;
		}
		return false;
	}

	public static boolean validDateTime(String time, String format) {
		if (time.length() != format.length()) {
			return false;
		} else {
			try {
				SimpleDateFormat df = new SimpleDateFormat(format);
				df.setLenient(false);
				df.parse(time);

				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public static boolean isNumberic(String inputString) {
		boolean rs = false;
		String reg = "^[0-9]{1,}$";
		try {
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs = match.matches();
			if (rs == false)
				return false;
			long number = Long.parseLong(inputString);
			if (number <= 0)
				return false;
		} catch (Exception e) {
			rs = false;
		}
		return rs;
	}

	public static boolean isValidHexColorCode(String str) {
		// Regex to check valid hexadecimal color code.
		String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the string is empty
		// return false
		if (isStrNull(str)) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given string
		// and regular expression.
		Matcher m = p.matcher(str);

		// Return if the string
		// matched the ReGex
		return m.matches();
	}

	/**
	 * Check if string input is contains special character
	 * @param str
	 * @param isAcceptSpace
	 * @return true if input string contain special character
	 */
	public static boolean isStrContainsSpecChar(String str, boolean isAcceptSpace) {
		String regex = "[^a-zA-Z0-9]";
		if (isAcceptSpace) {
			regex = "[^a-zA-Z0-9 ]";
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean isStringContainsSpecialCharacter = matcher.find();
		return isStringContainsSpecialCharacter;
	}

	/**
	 * validate transId/requestId format
	 * @param transId
	 * @param maxLength
	 * @return true if transId/requestId is valid: 0 < length < maxLength and only contains char, number, '-' '_' '.'
	 */
	public static boolean validateTransId(String transId, int maxLength) {
		if(isStrNull(transId)) {
			return false;
		}

		if (transId.length() > maxLength) {
			return false;
		}

		Pattern pattern = Pattern.compile("([^a-zA-Z0-9\\.\\_\\-])");
		Matcher matcher = pattern.matcher(transId);
		boolean isStringContainsSpecialCharacter = matcher.find();
		if (isStringContainsSpecialCharacter) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		
		String time = "2022-07-18 09:32:30";
		String format = "yyyy-MM-dd HH:mm:ss";
		System.out.println(validDateTime(time, format));
		
	}
}
