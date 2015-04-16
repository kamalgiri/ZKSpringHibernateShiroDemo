package com.kg.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.kg.demo.service.SidebarPage;
import com.kg.demo.service.SidebarPageConfig;

public class SidebarPageConfigAjaxBasedImpl implements SidebarPageConfig {

	HashMap<String, SidebarPage> pageMap = new LinkedHashMap<String, SidebarPage>();

	public SidebarPageConfigAjaxBasedImpl() {
		pageMap.put("zk", new SidebarPage("MySite", "MySite", "/imgs/site.png",
				"https://sites.google.com/site/learnprogramminglogic/"));

		pageMap.put("fn1", new SidebarPage("Home", "Home", "/imgs/fn.png",
				"/home.zul"));

		Subject user = SecurityUtils.getSubject();
		if (user.hasRole("admin")) {
			pageMap.put("fn2", new SidebarPage("User", "User", "", "/user.zul"));
		}

		pageMap.put("fn3", new SidebarPage("Profile", "Profile", "",
				"/profile.zul"));
	}

	public List<SidebarPage> getPages() {
		return new ArrayList<SidebarPage>(pageMap.values());
	}

	public SidebarPage getPage(String name) {
		return pageMap.get(name);
	}

}