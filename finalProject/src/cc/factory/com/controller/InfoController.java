package cc.factory.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.factory.com.dto.InfoDto;
import cc.factory.com.dto.InfoParam;
import cc.factory.com.dto.InfoReDto;
import cc.factory.com.service.InfoService;

@Controller
public class InfoController {

	@Autowired
	InfoService service;

	@RequestMapping(value = "infolist.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String infolist(Model model, InfoParam param) {

		//System.out.println("choice:" + param.getChoice());
		//System.out.println("searchWord:" + param.getSearchWord());

		// paging 처리
		int sn = param.getPageNumber(); // 현재 페이지
		int start = sn * param.getRecordCountPerPage() + 1; // 1 11 21
		int end = (sn + 1) * param.getRecordCountPerPage(); // 10 20 30

		param.setStart(start);
		param.setEnd(end);

		//System.out.println("param의 스타트 엔드Page 접수 ");

		List<InfoDto> list = service.getInfoList(param);
		model.addAttribute("infolist", list);

		System.out.println("list = "+ list.toString());

		//System.out.println("디비가서 리스트 받고 모델로 보냄 ");
		// 글의 총수
		int totalRecordCount = service.getInfoCount(param);
		//System.out.println("글의 총수 받기 ");

		model.addAttribute("pageNumber", sn);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);

		model.addAttribute("choice", param.getChoice());
		model.addAttribute("searchWord", param.getSearchWord());
		//System.out.println("다보내고타일즈로 이동직전 ");
		return "infolist.tiles";
	}

	@RequestMapping(value = "infowrite.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String infowrite(Model model) {

		return "infowrite.tiles";
	}

	@RequestMapping(value = "infowriteAf.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String infowriteAf(InfoDto info, Model model) throws Exception {
		System.out.println("infoWriteAf Dto = " + info.toString());
		if (info.getContent().equals("") || info.getTitle().equals("")) {
			return "infowrite.tiles";
		}
		boolean Y = service.writeInfo(info);
		//System.out.println("=====info WriteAf Y?? " + Y);
		if (Y) {
			return "redirect:/infolist.do";
		} else {
			return "redirect:/infowrite.do";
		}
	}

	@RequestMapping(value = "infodetail.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String infodetail(int seq, Model model) {

		InfoDto info = service.getInfo(seq);
		List<InfoReDto> infoReList = service.getInfoReList(seq);
		//System.out.println("Info Detail Dto = " + info.toString());

		model.addAttribute("info", info);
		model.addAttribute("infoReList", infoReList);

		return "infodetail.tiles";
	}


	 	
	 @RequestMapping(value = "writeRe.do", method = {RequestMethod.GET, RequestMethod.POST}) 
	 public String writeRe(InfoReDto infoRe, Model model) {
	
		 //System.out.println("InfoReDto~~~==== "+ infoRe.toString());
		 boolean Y = service.writeRe(infoRe); 
		 //System.out.println(Y);
		 if (Y) {
			 return "redirect:/infolist.do"; 
		}else {
			return "redirect:/infodetail.do"; 
		}
	 }
	 

	@RequestMapping(value = "infodelete.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteInfo(int seq, Model model) {
		service.deleteInfo(seq);
		return "redirect:/infolist.do";
	}

	@RequestMapping(value = "infoupdate.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String infoupdate(int seq, Model model) {
		InfoDto info = service.getInfo(seq);
		model.addAttribute("info", info);
		return "infoupdate.tiles";
	}

	@RequestMapping(value = "infoupdateAf.do", method = RequestMethod.POST)
	public String infoupdateAf(InfoDto info, Model model) {
		service.updateInfo(info);
		return "redirect:/infolist.do";
	}
}
