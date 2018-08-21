package ru.mamykin.exchange.core.exception

/**
 * Exception, which used for denote illegal operations on data source
 */
class DataSourceAccessException(message: String) : RuntimeException(message) {
}