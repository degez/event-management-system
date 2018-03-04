package com.yucel.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.yucel.model.BinNumberResourceConstant;
import com.yucel.resource.BinNumberResource;
import com.yucel.resource.BinNumberResourceAssembler;
import com.yucel.service.BinNumberChecker;


/**
 * Controller for Bin number operations
 * 
 * @author yucel.ozan
 *
 */
@RestController
@ExposesResourceFor(BinNumberResource.class)
@RequestMapping(value = BinNumberResourceConstant.ROOT)
public class BinNumberController {

	@Autowired
	BinNumberChecker binNumberChecker;
	
	
	@Resource
	BinNumberResourceAssembler binNumberResourceAssembler;

	@ResponseBody
	@RequestMapping(value = BinNumberResourceConstant.ID, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BinNumberResource> checkBinNumber(@PathVariable String id) {
		
		BinNumber binNumber = binNumberChecker.checkAndRetrieveBinNumber(Locale.TR, id, id);
		
		BinNumberResource resource = binNumberResourceAssembler.toResource(binNumber);
		return ResponseEntity.ok(resource);
	}

}
