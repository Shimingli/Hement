package com.shiming.hement.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * <p>
 *
 Identifies qualifier annotations. Anyone can define a new qualifier. A qualifier annotation:
 标识限定符注释。 Qualifier
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:42
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
