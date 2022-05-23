
package cn.exam.util;

import java.util.List;

/**
 * 回调函数
 * @File: CallBack
 * @Author pekosmile
 * @Date ##
 * @Description: 回调函数
 */
public interface CallBack<T> {
	/**
	 * 回调函数
	 */
    List<T> callBack();
}
