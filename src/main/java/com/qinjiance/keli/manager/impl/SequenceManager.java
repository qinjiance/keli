package com.qinjiance.keli.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import com.qinjiance.keli.manager.ISequenceManager;

/**
 * @author "Jiance Qin"
 * 
 * @date 2014年9月7日
 * 
 * @time 上午12:41:57
 * 
 * @desc
 * 
 */
@Service
public class SequenceManager implements ISequenceManager {

	@Autowired
	@Qualifier("userIdSeqGenerater")
	private MySQLMaxValueIncrementer userIdSeqGenerater;

	@Override
	public Long getUserIdSeq() {
		return userIdSeqGenerater.nextLongValue();
	}

}
