import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;

public class DistributeServer {

	public static void main(String[] args) throws Exception {
		
		DistributeServer server = new DistributeServer();
		
		// 1 连接zookeeper集群
		server.getConnect();
		
		// 2 注册节点
		server.regist(args[0]);
		
		// 3 业务逻辑处理
		server.business();
	}

	private void business() throws InterruptedException {
	
		Thread.sleep(Long.MAX_VALUE);
	}

	private void regist(String hostname) throws KeeperException, InterruptedException {
		// -e短暂的（断开连接就删除的）  -s 带编号的（自动在节点名称后面加字符串保证唯一）
		String path = zkClient.create("/servers/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println(hostname +"is online ");
		
	}

	private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	private void getConnect() throws IOException {
		
		zkClient = new ZooKeeper(connectString , sessionTimeout , new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
