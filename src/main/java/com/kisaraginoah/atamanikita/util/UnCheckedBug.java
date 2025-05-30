package com.kisaraginoah.atamanikita.util;

import java.lang.annotation.*;

/**
 * バグ等の検証をしていないアイテムに対して未検証のツールチップの追加をします。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UnCheckedBug {
}
