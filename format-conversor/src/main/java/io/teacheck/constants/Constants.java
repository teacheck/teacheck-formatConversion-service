package io.teacheck.constants;

public final class Constants {

    public static final int HTTP_SERVER_PORT = Integer.parseInt(System.getenv("HTTP_SERVER_PORT"));
    public static final int DB_SERVICE_PORT = Integer.parseInt(System.getenv("DB_SERVICE_PORT"));
    public static final String HTTP_SERVER_HOST = System.getenv("HTTP_SERVER_HOST");
    public static final String DB_SERVICE_HOST = System.getenv("DB_SERVICE_HOST");
}
