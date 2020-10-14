<!doctype html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />	
</head>
<body>
	<table style="background-color: #ddd;border: 1px solid #333;border-spacing: 0;margin: 0; padding: 0;font:14px microsoft YaHei,SimSun,Arial;width: 100%;text-align: center;">
	  <caption style="padding: 3px 5px;border-left: 1px solid #333;border-top: 1px solid #333;border-right: 1px solid #333;font-weight:bold;">
		 	<p>系统预警：【IP地址:<span>${info['host.ip']}</span>、主机名:<span>${info['host.name']}</span>、应用:<span>${info['host.app']}</span>】</p>
	  </caption>
	  <thead>
		<tr>
		  <th style="padding: 3px 5px;border-bottom: 1px solid #333;border-right: 1px solid #333;width: 15%;">序号</th>
		  <th style="padding: 3px 5px;border-bottom: 1px solid #333;border-right: 1px solid #333;width: 25%;">监控对象</th>
		  <th style="padding: 3px 5px;border-bottom: 1px solid #333;border-right: 1px solid #333;width: 25%;">警告阈值</th>
		  <th style="padding: 3px 5px;border-bottom: 1px solid #333;border-right: 1px solid #333;width: 25%;">当前使用率</th>
		</tr>
	  </thead>
	  <tbody>
		<tr>
		  <td style="padding: 3px 5px;border-right: 1px solid #333;">1</td>
		  <td style="padding: 3px 5px;border-right: 1px solid #333;">服务器CPU</td>
		  <td style="padding: 3px 5px;border-right: 1px solid #333;">${info['watch.cup.threshold']}%</td>
		  <td style="padding: 3px 5px;border-right: 1px solid #333;">${info['os.cpu.usage'] * 100}%</td>
		</tr>
		<tr>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">2</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">服务器内存</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">${info['watch.ram.threshold']}%</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">${info['os.ram.usage'] * 100}%</td>
		</tr>
		<tr>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">3</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">JVM内存</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">${info['watch.jvm.threshold']}%</td>
		  <td style="padding: 3px 5px;border-top: 1px solid #333;border-right: 1px solid #333;">${info['jvm.memory.usage'] * 100}%</td>
		</tr>
	  </tbody>
	  <tfoot>
		<td colspan="6" style="padding: 3px 15px;border-top: 1px solid #333;text-align: left;color:red;">注意：请及时处理系统预警。</td>
	  </tfoot>
	</table>
</body>