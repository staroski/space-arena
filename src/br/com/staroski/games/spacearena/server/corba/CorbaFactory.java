package br.com.staroski.games.spacearena.server.corba;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public final class CorbaFactory {

	public static EnergyItemProvider getEnergyItemProvider() throws Exception {
		// Cria e inicializa o ORB
		ORB orb = ORB.init(EnergyItemProvider.INIT_PARAMS, null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb.resolve_initial_references(EnergyItemProvider.SERVICE_NAME);
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = EnergyItemProvider.LOOKUP_NAME;
		EnergyItemProvider server = EnergyItemProviderHelper.narrow(ncRef.resolve_str(name));
		return server;
	}

	private CorbaFactory() {
	}
}
