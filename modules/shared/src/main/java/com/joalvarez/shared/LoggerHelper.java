package com.joalvarez.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public interface LoggerHelper {

    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    default void trace(String s) {
        this.getLogger().trace(s);
    }

    default void trace(String s, Object o) {
        this.getLogger().trace(s, o);
    }

    default void trace(String s, Object o, Object o1) {
        this.getLogger().trace(s, o, o1);
    }

    default void trace(String s, Object... objects) {
        this.getLogger().trace(s, objects);
    }

    default void trace(String s, Throwable throwable) {
        this.getLogger().trace(s, throwable);
    }

    default void trace(Marker marker, String s) {
        this.getLogger().trace(marker, s);
    }

    default void trace(Marker marker, String s, Object o) {
        this.getLogger().trace(marker, s, o);
    }

    default void trace(Marker marker, String s, Object o, Object o1) {
        this.getLogger().trace(marker, s, o, o1);
    }

    default void trace(Marker marker, String s, Object... objects) {
        this.getLogger().trace(marker, s, objects);
    }

    default void trace(Marker marker, String s, Throwable throwable) {
        this.getLogger().trace(marker, s, throwable);
    }

    default void debug(String s) {
        this.getLogger().debug(s);
    }

    default void debug(String s, Object o) {
        this.getLogger().debug(s, o);
    }

    default void debug(String s, Object o, Object o1) {
        this.getLogger().debug(s, o, o1);
    }

    default void debug(String s, Object... objects) {
        this.getLogger().debug(s, objects);
    }

    default void debug(String s, Throwable throwable) {
        this.getLogger().debug(s, throwable);
    }

    default void debug(Marker marker, String s) {
        this.getLogger().debug(marker, s);
    }

    default void debug(Marker marker, String s, Object o) {
        this.getLogger().debug(marker, s, o);
    }

    default void debug(Marker marker, String s, Object o, Object o1) {
        this.getLogger().debug(marker, s, o, o1);
    }

    default void debug(Marker marker, String s, Object... objects) {
        this.getLogger().debug(marker, s, objects);
    }

    default void debug(Marker marker, String s, Throwable throwable) {
        this.getLogger().debug(marker, s, throwable);
    }

    default void info(String s) {
        this.getLogger().info(s);
    }

    default void info(String s, Object o) {
        this.getLogger().info(s, o);
    }

    default void info(String s, Object o, Object o1) {
        this.getLogger().info(s, o, o1);
    }

    default void info(String s, Object... objects) {
        this.getLogger().info(s, objects);
    }

    default void info(String s, Throwable throwable) {
        this.getLogger().info(s, throwable);
    }

    default void info(Marker marker, String s) {
        this.getLogger().info(marker, s);
    }

    default void info(Marker marker, String s, Object o) {
        this.getLogger().info(marker, s, o);
    }

    default void info(Marker marker, String s, Object o, Object o1) {
        this.getLogger().info(marker, s, o, o1);
    }

    default void info(Marker marker, String s, Object... objects) {
        this.getLogger().info(marker, s, objects);
    }

    default void info(Marker marker, String s, Throwable throwable) {
        this.getLogger().info(marker, s, throwable);
    }

    default void warn(String s) {
        this.getLogger().warn(s);
    }

    default void warn(String s, Object o) {
        this.getLogger().warn(s, o);
    }

    default void warn(String s, Object... objects) {
        this.getLogger().warn(s, objects);
    }

    default void warn(String s, Object o, Object o1) {
        this.getLogger().warn(s, o, o1);
    }

    default void warn(String s, Throwable throwable) {
        this.getLogger().warn(s, throwable);
    }

    default void warn(Marker marker, String s) {
        this.getLogger().warn(marker, s);
    }

    default void warn(Marker marker, String s, Object o) {
        this.getLogger().warn(marker, s, o);
    }

    default void warn(Marker marker, String s, Object o, Object o1) {
        this.getLogger().warn(marker, s, o, o1);
    }

    default void warn(Marker marker, String s, Object... objects) {
        this.getLogger().warn(marker, s, objects);
    }

    default void warn(Marker marker, String s, Throwable throwable) {
        this.getLogger().warn(marker, s, throwable);
    }

    default void error(String s) {
        this.getLogger().error(s);
    }

    default void error(String s, Object o) {
        this.getLogger().error(s, o);
    }

    default void error(String s, Object o, Object o1) {
        this.getLogger().error(s, o, o1);
    }

    default void error(String s, Object... objects) {
        this.getLogger().error(s, objects);
    }

    default void error(String s, Throwable throwable) {
        this.getLogger().error(s, throwable);
    }

    default void error(Marker marker, String s) {
        this.getLogger().error(marker, s);
    }

    default void error(Marker marker, String s, Object o) {
        this.getLogger().error(marker, s, o);
    }

    default void error(Marker marker, String s, Object o, Object o1) {
        this.getLogger().error(marker, s, o, o1);
    }

    default void error(Marker marker, String s, Object... objects) {
        this.getLogger().error(marker, s, objects);
    }

    default void error(Marker marker, String s, Throwable throwable) {
        this.getLogger().error(marker, s, throwable);
    }

    default String getName() {
        return "framework";
    }

    default boolean isTraceEnabled() {
        return true;
    }

    default boolean isTraceEnabled(Marker marker) {
        return true;
    }

    default boolean isDebugEnabled() {
        return true;
    }

    default boolean isDebugEnabled(Marker marker) {
        return true;
    }

    default boolean isInfoEnabled() {
        return true;
    }

    default boolean isInfoEnabled(Marker marker) {
        return true;
    }

    default boolean isWarnEnabled() {
        return true;
    }

    default boolean isWarnEnabled(Marker marker) {
        return true;
    }

    default boolean isErrorEnabled() {
        return true;
    }

    default boolean isErrorEnabled(Marker marker) {
        return true;
    }
}