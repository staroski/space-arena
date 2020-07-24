/** EnergyItemProviderServer.java **/
package br.com.staroski.games.spacearena.server.corba;

import java.io.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

import br.com.staroski.games.*;

public class ItemProviderServer extends EnergyItemProviderPOA {

	public static void main(String[] args) {
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						String path = System.getProperty("java.home") + File.separator + "bin";
						String exec = path + File.separator + "orbd";
						CommandLine cmd = new CommandLine(exec);
						cmd.execute();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			Thread.sleep(3);

			// Cria e inicializa o ORB
			ORB orb = ORB.init(EnergyItemProvider.INIT_PARAMS, null);

			// Cria a implementação e registra no ORB
			EnergyItemProviderPOA object = new ItemProviderServer();

			// Ativa o POA
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references(EnergyItemProvider.ROOT_POA));
			rootpoa.the_POAManager().activate();

			// Pega a referência do servidor
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(object);
			EnergyItemProvider href = EnergyItemProviderHelper.narrow(ref);

			// Obtém uma referência para o servidor de nomes
			org.omg.CORBA.Object objRef = orb.resolve_initial_references(EnergyItemProvider.SERVICE_NAME);
			NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

			// Registra o servidor no servico de nomes
			String name = EnergyItemProvider.LOOKUP_NAME;
			NameComponent path[] = namecontextRef.to_name(name);
			namecontextRef.rebind(path, href);

			System.out.println("Servidor corba aguardando requisicoes...");

			// Aguarda chamadas dos clientes
			orb.run();
			System.out.println("objeto do tipo " + object.getClass().getName() + " disponivel no servico " + EnergyItemProvider.SERVICE_NAME + " pelo nome "
					+ EnergyItemProvider.LOOKUP_NAME);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private CorbaEnergyItem item;

	private ItemProviderServer() {
		item = new CorbaEnergyItem();
	}

	@Override
	public void createNewItem() {
		item = new CorbaEnergyItem();
		System.out.println("new corba energy item created");
	}

	@Override
	public int getEnergy() {
		System.out.println("retrieving corba energy item's energy amount");
		return item.energy;
	}

	@Override
	public long getId() {
		System.out.println("retrieving corba energy item's id");
		return item.id;
	}

}
