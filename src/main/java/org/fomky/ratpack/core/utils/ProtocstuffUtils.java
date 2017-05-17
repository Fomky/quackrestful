package org.fomky.ratpack.core.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

@SuppressWarnings("unchecked")
public class ProtocstuffUtils {

	public static <T> T byte2Bean(byte[] data, Class<T> clazz) {
		Schema<T> schema = RuntimeSchema.getSchema(clazz);
		T bean = null;
		try {
			bean = schema.newMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProtobufIOUtil.mergeFrom(data, bean, schema);
		return bean;
	}

	public static <T> byte[] bean2Byte(T bean, Class<?> clazz) {
		LinkedBuffer buffer = getApplicationBuffer();
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(clazz);
		return ProtobufIOUtil.toByteArray(bean, schema, buffer);
	}

	private static final int bufferSize = 2048;

	private static final ThreadLocal<LinkedBuffer> localBuffer = ThreadLocal.withInitial(() -> LinkedBuffer.allocate(bufferSize));

	private static LinkedBuffer getApplicationBuffer() {
		return localBuffer.get().clear();
	}
}
