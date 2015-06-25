package com.bingo.socket;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.lang.ArrayUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.poi.util.ArrayUtil;

import com.bingo.core.Constants;
import com.kenai.jffi.Array;

public class WorldPacket {

	private short opCode;
	private IoBuffer data;

	public WorldPacket() {
	}

	public WorldPacket(Short opCode) {
		super();
		this.opCode = opCode;
		data = IoBuffer.allocate(Constants.WORLD_PACKET_MAX_LENGTH);
	}

	public void DeSerializePakcet(Object msg) {
		IoBuffer recvBuffer = (IoBuffer) msg;
		byte[] orginalData = new byte[recvBuffer.limit()];
		ArrayUtils.reverse(orginalData);

		IoBuffer buffer = IoBuffer.wrap(orginalData);

		Short size = buffer.getShort();
		Short opCode = buffer.getShort();
		byte[] data = new byte[Constants.WORLD_PACKET_MAX_LENGTH];
		buffer.get(data, size, size - 4);
		this.setData(IoBuffer.wrap(data));
		this.setOpCode(opCode);
	}

	public IoBuffer SerializePacket() {
		IoBuffer buffer = IoBuffer.allocate(Constants.WORLD_PACKET_MAX_LENGTH);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.getData().flip();

		// header
		Integer size = this.getData().limit() + 4;
		buffer.putShort(size.shortValue());
		buffer.putShort(this.getOpCode());
		// body
		buffer.put(this.getData());

		buffer.flip();
		return buffer;
	}

	public short getOpCode() {
		return opCode;
	}

	public void setOpCode(short opCode) {
		this.opCode = opCode;
	}

	public IoBuffer getData() {
		return data;
	}

	public void setData(IoBuffer data) {
		this.data = data;
	}

	public static void main(String[] args) {
		String str = " \t︻︼︽︾〒↑↓☉⊙●〇◎¤★☆■▓「」『』◆◇▲△▼▽◣◥◢◣◤ ◥№↑↓→←↘↙Ψ※㊣∑⌒∩【】〖〗＠ξζω□∮〓※》∏卐√ ╳々♀♂∞①ㄨ≡╬╭╮╰╯╱╲ ▂ ▂ ▃ ▄ ▅ ▆ ▇ █ ▂▃▅▆█ ▁▂▃▄▅▆▇█▇▆▅▄▃▂▁！@#￥%……&*（）——+-=、、。，/.,\\][;\':\"=-`~/*-!$%^&*_";
		
	}
}
