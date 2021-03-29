package com.hd.hse.business.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName: DownFilelistener (下载文件)<br/>
 * date: 2014年9月5日 <br/>
 * 
 * @author Administrator
 * @version
 */
public abstract class DownFilelistener extends AbstractWebListener {

	private URLConnection connection;
	private InputStream inputStream;
	private OutputStream outputStream;
	private int FileLength;
	private int DownedFileLength = 0;

	@Override
	public Object action(String action, Object... args) throws HDException {
		try {
			super.action(action, args);
			// 获取下载url
			String downUrl = getDownUrl();
			if (StringUtils.isEmpty(downUrl)) {
				this.setWritelog("请设置下载文件路径!");
				throw new HDException("请设置下载文件路径!");
			}
			// 获取保存路径
			String savePath = getSavePath();
			if (StringUtils.isEmpty(savePath)) {
				this.setWritelog("请设置保存文件路径!");
				throw new HDException("请设置保存文件路径!");
			}
			String filename = setDownFile(downUrl, savePath);
			this.SendMessage(IMessageWhat.END, 99, 100, savePath
					+ File.separator + filename);
		} catch (HDException e) {
			this.setWritelog("下载文件失败", e);
			this.SendMessage(IMessageWhat.ERROR, 9, 9,e.getMessage().indexOf("超时") > 0 ? e.getMessage(): "下载失败,请联系管理员");
		}
		return null;
	}

	/**
	 * getDownUrl:(下载url). <br/>
	 * date: 2014年9月9日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract String getDownUrl();

	/**
	 * getSavePath:(保存路径). <br/>
	 * date: 2014年9月9日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract String getSavePath();

	/**
	 * setDownFile:(下载文件). <br/>
	 * date: 2014年9月13日 <br/>
	 * 
	 * @author lxf
	 * @param url
	 *            下载路径
	 * @param savepath
	 *            保存路径
	 * @return 文件名
	 * @throws HDException
	 */
	public String setDownFile(String url, String savepath) throws HDException {
//		if(!isConnect(url))
//		{
//			throw new HDException("请求超时,请设置网络连接!");
//		}
		setInputStream(url);
		String filename = getFileName(url);
		// 保存文件
		setSaveFile(savepath, filename);
		return filename;
	}

	/**
	 * setInputStream:(获得保存的流). <br/>
	 * date: 2014年9月13日 <br/>
	 * 
	 * @author Administrator
	 * @param downUrl
	 * @throws HDException
	 */
	private void setInputStream(String downUrl) throws HDException {
		try {

			downUrl = setEncodeURL(downUrl, "UTF-8");

			URL url = new URL(downUrl);
			connection = url.openConnection();
			if (connection.getReadTimeout() == 5) {
				throw new HDException("链接网络超时!");
			}
			inputStream = connection.getInputStream();

		} catch (MalformedURLException e1) {
			this.setWritelog("请设置有效的url!" + downUrl);
			throw new HDException("请设置有效的url!");
		} catch (IOException e) {
			this.setWritelog("请设置有效的url!");
			String str = e.getMessage();
			throw new HDException("请设置有效的url!");

		}
	}

	/**
	 * setSaveFile:(保存文件). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lxf
	 * @param savePAth
	 * @param filename
	 * @throws HDException
	 */
	private void setSaveFile(String savePAth, String filename)
			throws HDException {
		// 判断目录是否存在
		File file1 = new File(savePAth);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		savePAth = savePAth + File.separator + filename;
		File file = new File(savePAth);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			this.setWritelog("创建文件失败!", e);
		}
		try {
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[50 * 1024];
			FileLength = connection.getContentLength();

			int len;
			int sum = 0;
			int oldpercent = 1;
			int percent = 2;
			while ((len = inputStream.read(buffer)) != -1) {
				sum = sum + len;
				percent = Math
						.round((((float) sum / (float) FileLength) * 100));
				if (percent >= 100) {
					percent = 99;
				}
				if (oldpercent != percent)
					this.SendMessage(IMessageWhat.PROCESSING, oldpercent,
							percent, "下载文件中...");
				oldpercent = percent;
				outputStream.write(buffer, 0, len);

			}
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			this.setWritelog("保存文件失败", e);
			throw new HDException("保存文件失败");

		} catch (IOException e) {
			this.setWritelog("保存文件失败", e);
			throw new HDException("保存文件失败");
		}
	}

	private String getFileName(String path) {
		String str;
		int start = path.lastIndexOf("/");
		int end = path.length();
		if (start != -1 && end != -1) {
			str = path.substring(start + 1, end);
		} else {
			str = "hse-app.apk";
		}
		return str;
	}

	private String setEncodeURL(String url, String encode)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		StringBuilder noAsciiPart = new StringBuilder();
		for (int i = 0; i < url.length(); i++) {
			char c = url.charAt(i);
			if (c > 255) {
				noAsciiPart.append(c);
			} else {
				if (noAsciiPart.length() != 0) {
					sb.append(URLEncoder.encode(noAsciiPart.toString(), encode));
					noAsciiPart.delete(0, noAsciiPart.length());
				}
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * isConnect:(判断网路是否畅通). <br/>
	 * date: 2014年9月15日 <br/>
	 * 
	 * @author lxf
	 * @param url
	 * @return
	 */
	public boolean isConnect(String url) {
		boolean ret = false;
		try {
			String ip = null;
			ip = getIp(url);
			if (StringUtils.isEmpty(ip)) {
				return false;
			}
			Process p = Runtime.getRuntime().exec(
					"/system/bin/ping -c " + 2 + " " + ip);// 10.83.50.111
															// m_strForNetAddress
			int status = p.waitFor();
			if (status == 0) {
				ret = true;
			}
		} catch (IOException e) {
			this.setWritelog("判断网络是否连接报错", e);
		} catch (InterruptedException e) {
			this.setWritelog("判断网络是否连接报错", e);
		}
		return ret;
	}

	private String getIp(String url) {
		String ip;
		String[] str = url.replace("http://", "").split("/");
		ip = str[0].split(":")[0];
		return ip;

	}

}
