<div class="row animated fadeInRight">
	<div class="col-sm-4 col-md-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<i class="fa fa-th-list"></i> 服务器信息
			</div>
			<!-- List group -->
			<ul class="list-group">
			    <li class="list-group-item">
			 		<table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">计算机名：</td>
				            <td class="text-left">${os["host.computer.name"]}</td>
				          </tr>
				         <tr>
				            <td class="text-right td-left">计算机域名：</td>
				            <td class="text-left">${os["host.userdomain"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">本地主机名：</td>
				            <td class="text-left">${os["host.name"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">操作系统：</td>
				            <td class="text-left">${jvm["os.name"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">版本号：</td>
				            <td class="text-left">${os["os.version"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">系统位数：</td>
				            <td class="text-left">${os["os.datamodel"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">系统补丁级别：</td>
				            <td class="text-left">${os["os.patch.level"]}</td>
				          </tr>
				        </tbody>
			      	</table>
			   </li>
			</ul>
		</div>
	</div>
	<div class="col-sm-8 col-sm-8">
	  	<div class="panel panel-danger">
			<div class="panel-heading">
				<i class="fa fa-coffee"></i> JVM 概要
			</div>
			<!-- List group -->
			<ul class="list-group">
			    <li class="list-group-item">
				     <table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">虚拟机：</td>
				            <td class="text-left td-left-val">${jvm["java.vm.name"]} 版本 ${jvm["java.vm.version"]}</td>
				            <td class="text-right td-left">运行时间：</td>
				            <td>${jvm["jvm.runtime.StartTime"]}分钟</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">供应商：</td>
				            <td class="text-left td-left-val">${jvm["java.vm.vendor"]}</td>
				            <td class="text-right td-left">JVM名称：</td>
				            <td>${jvm["java.vm.pid"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">JIT 编译器：</td>
				            <td class="text-left td-left-val">${jvm["jvm.compilation.name"]}</td>
				            <td class="text-right td-left">总编译时间：</td>
				            <td>${jvm["jvm.compilation.totalCompilationTime"]/1000}秒</td>
				          </tr>
				        </tbody>
			      	</table>
			    </li>
			    <li class="list-group-item">
			    	<table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">活动线程：</td>
				            <td class="text-left td-left-val">${jvm["jvm.thread.ThreadCount"]}</td>
				            <td class="text-right td-left">已加装当前类：</td>
				            <td>${jvm["jvm.class.LoadedCount"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">峰值：</td>
				            <td class="text-left td-left-val">${jvm["jvm.thread.PeakThreadCount"]}</td>
				            <td class="text-right td-left">已加载类总数：</td>
				            <td>${jvm["jvm.class.TotalLoadedCount"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">守护程序线程：</td>
				            <td class="text-left td-left-val">${jvm["jvm.thread.DaemonThreadCount"]}</td>
				            <td class="text-right td-left">已卸载类总数：</td>
				            <td>${jvm["jvm.class.UnloadedCount"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">启动的线程总数：</td>
				            <td class="text-left td-left-val">${jvm["jvm.thread.TotalStartedThreadCount"]}</td>
				            <td class="text-right td-left"></td>
				            <td></td>
				          </tr>
				        </tbody>
			      	</table>
			    </li>
			    <li class="list-group-item">
			    	<table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">当前堆大小：</td>
				            <td class="text-left td-left-val">${jvm["jvm.memory.used"]}KB</td>
				            <td class="text-right td-left">提交的内存：</td>
				            <td>${jvm["jvm.memory.total"]}KB</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">最大堆大小：</td>
				            <td class="text-left td-left-val">${jvm["jvm.memory.max"]}KB</td>
				            <td class="text-right td-left">暂挂最终处理：</td>
				            <td>0对象</td>
				          </tr>
				          [#list gcs as gc]
						  <tr>
				            <td class="text-right td-left">垃圾收集器：</td>
				            <td colspan="3">名称 = '${gc["jvm.gc.name"]}', 收集 = ${gc["jvm.gc.count"]}, 总花费时间 = ${gc["jvm.gc.time"]/1000} 秒</td>
				          </tr>
						  [/#list]
				        </tbody>
			      	</table>
			    </li>
			    <li class="list-group-item">
			    	<table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">操作系统：</td>
				            <td class="text-left td-left-val">${jvm["os.name"]} ${jvm["os.version"]}</td>
				            <td class="text-right td-left">总物理内存：</td>
				            <td>${jvm["os.ram.total"]}KB</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">体系结构：</td>
				            <td class="text-left td-left-val">${jvm["os.arch"]}</td>
				            <td class="text-right td-left">空闲物理内存：</td>
				            <td>${jvm["os.ram.free"]}KB</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">处理程序数：</td>
				            <td class="text-left td-left-val">${jvm["os.cores"]}</td>
				            <td class="text-right td-left">总交换空间：</td>
				            <td>${jvm["os.swap.total"]}KB</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">提交的虚拟内存：</td>
				            <td class="text-left td-left-val">${jvm["os.swap.used"]}KB</td>
				            <td class="text-right td-left">空闲交换空间：</td>
				            <td>${jvm["os.swap.free"]}KB</td>
				          </tr>
				        </tbody>
			      	</table>
			    </li>
			    <li class="list-group-item">
			    	<table class="table table-condensed">
				        <tbody>
				          <tr>
				            <td class="text-right td-left">VM 参数：</td>
				            <td colspan="3">${jvm["java.vm.options"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">类路径：</td>
				            <td colspan="3">${jvm["java.class.path"]}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">库路径：</td>
				            <td colspan="3">${jvm['java.library.path']}</td>
				          </tr>
				          <tr>
				            <td class="text-right td-left">引导类路径：</td>
				            <td colspan="3">${jvm["java.boot.class.path"]}</td>
				          </tr>
				        </tbody>
			      	</table>
			    </li>
			</ul>
		 </div>
	</div>
</div>