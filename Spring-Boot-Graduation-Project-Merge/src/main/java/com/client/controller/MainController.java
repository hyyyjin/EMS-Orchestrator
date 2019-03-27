package com.client.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.client.domain.EventDomain;
import com.client.domain.ReportDescriptionDomain;
import com.client.domain.ReportDomain;
import com.client.service.RepositoryService;
import com.client.structure.Subnode;

/**
 * handle request-jsp mapping
 */
@Controller
public class MainController {
	@Autowired
	RepositoryService repoService;
	/*
	 * change this google api key to yours.
	 */
	private static final String googleKey = "AIzaSyA-6hECTt9Q5DvhgQuaAk158tbPaotXw4o";
	
	@RequestMapping("/login")
	public String login () {
		return "login";
	}
	
	@RequestMapping("/menu")
	public String emsMenu(Model model) {
		List<String> nameList = repoService.findEmaList();	// read list of EMA from DB
		Collections.sort(nameList);
		model.addAttribute("nameList", nameList);
		model.addAttribute("key", googleKey);
		
		return "map_menu";
	}
	
	@RequestMapping("/ema")
	public String emaInfo(Model model, @RequestParam("eid")Optional<String> eid) {
		// validation check
		if (!eid.isPresent())
			return "redirect:/menu";
		String emaId = eid.get();
		if (emaId.equals(""))
			return "redirect:/menu";
		ReportDomain report = repoService.findReport(emaId);	// report of 'eid' in DB
		if (report == null)
			return "redirect:/menu";
		
		// ema information
		List<Subnode> deviceList = new ArrayList<>();
		for (ReportDescriptionDomain rd: report.getReportDescription()) {
			if (!rd.getDeviceType().equals("EMA"))	// except EMA, collect all type of subnode
				// add subnode to list
				deviceList.add(new Subnode(rd.getDeviceType(), rd.getrID()));
		}
		Collections.sort(deviceList);
		model.addAttribute("deviceList", deviceList);
		model.addAttribute("eid", emaId);
		model.addAttribute("count", deviceList.size());
		model.addAttribute("key", googleKey);

		return "ema_graph";
	}
	
	@RequestMapping("/subnode")
	public String subInfo(Model model, @RequestParam("eid")Optional<String> eid, 
			@RequestParam("did")Optional<String> did) {
		// validation check
		if (!eid.isPresent())
			return "redirect:/menu";
		String emaId = eid.get();
		if (!did.isPresent())
			return "redirect:/ema?eid="+emaId;
		String deviceId = did.get();
		ReportDomain report = repoService.findReport(emaId);	// report of 'eid' in DB
		if (report == null)
			return "redirect:/menu";

		// subnode information
		List<Subnode> deviceList = new ArrayList<>();
		String itemUnit = null;
		for (ReportDescriptionDomain rd: report.getReportDescription()) {
			if (!rd.getDeviceType().equals("EMA")) {	// except EMA
				deviceList.add(new Subnode(rd.getDeviceType(), rd.getrID()));
				if (deviceId.equals(rd.getrID())) {
					// target deviceId data
					itemUnit = rd.getItemUnits();
				}
			}
		}
		Collections.sort(deviceList);
		model.addAttribute("eid", emaId);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("deviceList", deviceList);
		model.addAttribute("itemUnit", itemUnit);
		
		return "sub_graph";
	}
	
	@RequestMapping("/report")
	public String report(Model model, @RequestParam("eid")Optional<String> eid) {
		// validation check
		if (!eid.isPresent())
			return "redirect:/menu";
		String emaId = eid.get();
		EventDomain event = repoService.findEvent(emaId);
		if (event == null)
			return "redirect:/menu";
		
		String itemUnit = event.getEventSignals().get(0).getUnit();
		double price = event.getEventSignals().get(0).getPrice();
		model.addAttribute("eid", emaId);
		model.addAttribute("itemUnit", itemUnit);
		model.addAttribute("price", price);
		
		return "report_graph";
	}
	
	@RequestMapping("/dr")
	public String monitor(Model model, @RequestParam("type")Optional<Integer> opt) {
		int type = 0;
		if (opt.isPresent())
			type = opt.get();
		if (type < 0 || type > 2)
			return "redirect:/menu";
		
		model.addAttribute("type", type);
		switch(type) {
		case 0:	// without any algorithm
			model.addAttribute("desc", "none");
			break;
		case 1:	// with greedy
			model.addAttribute("desc", "Greedy");
			break;
		case 2:	// with knapsack
			model.addAttribute("desc", "Knapsack");
			break;
		}
		
		return "dr_graph";
	}
}
