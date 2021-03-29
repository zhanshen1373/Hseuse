package com.hd.hse.common.module.phone.ui.module.measurefragment;

import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;

import java.util.List;

/**
 * ClassName: MeasureAatapterInterface (措施适配器接口)<br/>
 * date: 2015年1月27日  <br/>
 *
 * @author lxf
 */
public interface MeasureAatapterInterface {

    /**
     * getSelectedValues:(获取修改过+当前选中的数据源). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<SuperEntity> getSelectedValues();

    /**
     * getSourceValues:(获取数据源). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<SuperEntity> getSourceValues();

    /**
     * updateValues:(更改  传入数据的 （修改ismod）状态). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @param values
     * @author lxf
     */
    public void updateValues(List<SuperEntity> values);

    /**
     * getCurrentSelectedValues:(获取当前选中的数据源). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract List<SuperEntity> getCurrentSelectedValues();

    /**
     * updateCurrentValues:((更改  传入数据的(选中) 状态). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @param values
     * @author lxf
     */
    public abstract void updateCurrentValues(List<SuperEntity> values);

    /**
     * 得到存在pc标记的对象
     * @return
     */
    public abstract List<SuperEntity> getHavePcStatusValues();

    /**
     * nextItem:(下一条). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @author lxf
     */
    public void nextItem();

    /**
     * previousItem:(上一条). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @author lxf
     */
    public void previousItem();

    /**
     * getAdapter:(获取适配器). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @author lxf
     */
    public Object getAdapter();

    /**
     * setIsselectAttr:(设置当前选中的虚拟字段属性). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @param isselectAttr
     * @author lxf
     */
    public void setIsselectAttr(String isselectAttr);

    /**
     * setIEventListener:(设置事件). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @param listener
     * @author lxf
     */
    public void setIEventListener(IEventListener listener);


}
