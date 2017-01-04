package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import ssa.Transaction;

public class Main {

	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
	private static final String user = "donato";
	private static final String password = "donato";
	private static final String CONTAINER = "RuleTest";

	public static void main(String[] args) {
		callKieServer();
	}

	@SuppressWarnings("rawtypes")
	public static void callKieServer() {
		try {
			
			KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, user, password);
			// Marshalling
			Set<Class<?>> extraClasses = new HashSet<Class<?>>();
			extraClasses.add(Transaction.class);
			config.addExtraClasses(extraClasses);
			config.setMarshallingFormat(MarshallingFormat.JSON);
			
			
			KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
			RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

			KieCommands cmdFactory = KieServices.Factory.get().getCommands();
			List<Command> commands = new ArrayList<Command>();

			Transaction transaction = new Transaction();
			transaction.setAmount(102.0);

			commands.add(cmdFactory.newInsert(transaction, "transaction"));
			commands.add(cmdFactory.newFireAllRules());
			BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, "ksessionDrlRules");

			ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER, command);
			ExecutionResults results = response.getResult();
			Collection<String> identifiers = results.getIdentifiers();
			for (String identifier : identifiers) {
				Object fact = results.getValue(identifier);
				System.out.println(fact);
			}
			
			//System.out.println(">"+transactionOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}