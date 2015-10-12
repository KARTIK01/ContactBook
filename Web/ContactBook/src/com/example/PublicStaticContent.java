package com.example;

public class PublicStaticContent {

	public static final String LOGIN = "login";
	public static final String LOGIN_FALSE_MESSAGE = "loginmessage";
	public static final String RECORD_DELETED = "recorddeleted";
	public static final String RECORD_NOT_DELETED_MESSAGE = "deletedmessage";
	public static String USER_NAME = "username";
	public static String RECORD_UPDATED = "recordupdated";
	public static String RECORD_INSERTED = "recordinserted";
	public static String RECORD_NOT_INSERTED_MESSAGE = "recordinsertedmessage";

	public static String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
