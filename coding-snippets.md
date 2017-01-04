# Variable

    kcontext.setVariable("list",list);

# XStream utility

Constructor

	XStream xStream = new XStream();
	xStream.fromXML(xml, this);

String

	public String toString() {
		XStream xStream = new XStream();
		return xStream.toXML(this);
	}

# Case Management

## Trigger adhoc fragment
			CaseMgmtService caseMgmtService = new CaseMgmtUtil(engine);
			String[] adHocFragmentNames = caseMgmtService.getAdHocFragmentNames(646);
		
			for (String frag : adHocFragmentNames) {
				System.out.println(">>> "+frag);
			}
			caseMgmtService.triggerAdHocFragment(646, "Segreteria Prepara Pubblicazione");

## Ad Hoc Subprocess

In the designer the user has possibility to set 'AdHocOrdering' property for Ad Hoc Sub-Processes: parallel and sequential.
Nevertheless, core engine only supports parallel execution, property should be hidden in designer.