package accountancy.server;

import accountancy.server.errors.HttpException;

import java.io.IOException;

@FunctionalInterface interface Controller {

    Object run() throws IOException, HttpException;
}
