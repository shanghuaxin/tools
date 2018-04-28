package com.wangzhixuan.service;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface SysLogService extends IService<SysLog> {

	PageInfo selectDataGrid(PageInfo pageInfo);

}