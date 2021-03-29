package com.hd.hse.common.component.phone.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.listener.EnsureClickListener;
import com.hd.hse.entity.time.ServerDateManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @author zhulei
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 * dateTimePicKDialog=new
 * DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 * dateTimePicKDialog.dateTimePicKDialog(inputDate);
 * <p>
 * } });
 */
public class DateTimePickDialogUtil implements OnDateChangedListener,
        OnTimeChangedListener {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;
    private Button cancel;
    private Button sure;
    private EnsureClickListener ensureClickListener;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity     ：调用的父activity
     * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DateTimePickDialogUtil(Activity activity, String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;

    }

    public void setOnEnsureClickListener(EnsureClickListener ensureClickListener) {
        this.ensureClickListener = ensureClickListener;
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime)) && initDateTime.length() > 15) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
			/*initDateTime = calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH) + " "
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
					+ calendar.get(Calendar.MINUTE)+":"+
					+ calendar.get(Calendar.MILLISECOND);*/
            initDateTime = ServerDateManager.getCurrentTime();
            calendar = this.getCalendarByInintData(initDateTime);
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog dateTimePicKDialog(final TextView inputDate) {
        View dateTimeLayout = (View) activity
                .getLayoutInflater().inflate(R.layout.hd_hse_common_phone_datetime, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.hd_hse_common_datetime_datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.hd_hse_common_datetime_timepicker);
        sure = (Button) dateTimeLayout.findViewById(R.id.hd_hse_phone_datetimepick_sure_b);
        cancel = (Button) dateTimeLayout.findViewById(R.id.hd_hse_phone_datetimepick_cancel_b);
        init(datePicker, timePicker);
        timePicker.setOnTimeChangedListener(this);
        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout).show();

        onDateChanged(null, 0, 0, 0);
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
                        ad.dismiss();
                        if (ensureClickListener != null) {
                            ensureClickListener.OnEnsureClick(inputDate);
                        }
                    }
                }.onClick(ad, DialogInterface.BUTTON_POSITIVE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ad.dismiss();
                    }
                }.onClick(ad, DialogInterface.BUTTON_NEGATIVE);
            }
        });
        return ad;
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), curentMilis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    /**
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();
        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
        String yearStr = initDateTime.substring(0, 4); // 年份
        String monthStr = initDateTime.substring(5, 7); // 月
        String dayStr = initDateTime.substring(8, 10); // 日
        String hourStr = initDateTime.substring(11, 13); // 时
        String minuteStr = initDateTime.substring(14, 16); // 分
        String milissStr = initDateTime.substring(17, 19); // 秒

        if (yearStr.matches("[0-9]+") && monthStr.matches("[0-9]+") && dayStr.matches("[0-9]+") && hourStr.matches("[0-9]+") && minuteStr.matches("[0-9]+") && milissStr.matches("[0-9]+")) {
            int currentYear = Integer.valueOf(yearStr.trim()).intValue();
            int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
            int currentDay = Integer.valueOf(dayStr.trim()).intValue();
            int currentHour = Integer.valueOf(hourStr.trim()).intValue();
            int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();
            curentMilis = Integer.valueOf(milissStr.trim()).intValue();
            calendar.set(currentYear, currentMonth, currentDay, currentHour,
                    currentMinute, curentMilis);
        }
        return calendar;
    }

    /**
     * curentMilis:TODO(当前时间对应的秒).
     */
    private int curentMilis = 0;

}
