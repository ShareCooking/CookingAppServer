package com.cooking.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Paging {

//	public static String getDataPaging(SearchVO searchVO, int totCnt){
//		return makePaging(searchVO, totCnt, true);
//	}
//	
//	public static String getPaging(SearchVO searchVO, int totCnt){
//		return makePaging(searchVO, totCnt, false);
//	}
//
//	private static String makePaging(SearchVO searchVO, int totCnt, boolean isDataPaging) {
//		
//		StringBuffer sb = new StringBuffer();
//
//		try{
//			if(totCnt > 0){
//				
//				int pageCurIndex 	= searchVO.getPageCurIndex();	// 현재 페이지 Index
//				int pageRowSize 	= searchVO.getPageRowSize();	// 페이지 Row 수
//				int pageBlockSize 	= searchVO.getPageBlockSize();	// Block Size
//				
//				searchVO.setFirstIndex((pageCurIndex - 1) * pageRowSize);
//				
//				int nStartPage 		= 0;							// Block상에서 시작 페이지 Index
//				int nEndPage 		= 0;							// Block상에서 마지막 페이지 Index
//				
//				int nBlockGrpSize 	= (int)Math.ceil((float)totCnt / (float)(pageRowSize * pageBlockSize));		// 전체 Block Group Size
//				int nCurrentGrp 	= (int)Math.ceil((float)pageCurIndex / (float)pageBlockSize );				// 현재 페이지가 속한 Block Group 번호
//				int nTotalPageSize 	= (int)Math.ceil((float)totCnt / (float)pageRowSize );						// 전체 페이지수
//				
//				nStartPage = (nCurrentGrp * pageBlockSize) - (pageBlockSize - 1);
//	
//				// 현재 Block Group이 전체 Block Group과 같은 경우 마지막 페이지 Index를 전체 페이지수로 대체
//				if( nCurrentGrp == nBlockGrpSize ){
//		            nEndPage = nTotalPageSize;
//		        }
//				
//		        // 현재 Block Group이 전체 Block Group보다 작을 경우 마지막 페이지 Index를 현재 Block Group에 Block Size를 곱한수로 대체
//		        else if(nCurrentGrp < nBlockGrpSize){
//		            nEndPage = nCurrentGrp * pageBlockSize;
//		        }
//				
//				sb.append("<ul>");
//				
//				if(nStartPage > 1) {
//					sb.append(" <li onClick=\"goPage(1);\" class=\"prePre\">&nbsp;</li>");
//				}
//				 
//				if(nCurrentGrp != 1 ) {
//			        int nBefPage = (nCurrentGrp - 1) * pageBlockSize;
//			        sb.append(" <li onClick=\"goPage("+nBefPage+");\" class=\"pre\">&nbsp;</li>");
//			    } else {
//					sb.append(" <li class=\"pre\">&nbsp;</li>");
//			    }
//				
//				for(int i=nStartPage; i<=nEndPage; i++){
//			        if(i == pageCurIndex) { // 현재 페이지라면 
//			        	sb.append(" <li class=\"on\">"+ i + "</li> ");
//			        } else if(i != pageCurIndex) { // 현재 페이지가 아니라면
//			        	sb.append(" <li onClick=\"goPage("+i+");\">"+i+"</li> ");
//			        }
//			    }
//				 // 현재 Block Group이 전체 Block Group이 아니고 또 리스트가 0이 아닐때 즉 데이터가 존재할때 [다음]출력
//			    if(nCurrentGrp != nBlockGrpSize && totCnt != 0){
//			        int nNextPage = nCurrentGrp*pageBlockSize+1;
//			        sb.append(" <li onClick=\"goPage("+nNextPage+");\" class=\"next\">&nbsp;</li> ");
//			    }else{
//			    	sb.append(" <li class=\"next\">&nbsp;</li> ");
//			    }
//			    
//			    if(nEndPage < nTotalPageSize) {
//			    	sb.append(" <li onClick=\"goPage("+nTotalPageSize+");\" class=\"nextNext\">&nbsp;</li>");
//			    }
//			    
//			    sb.append("</ul>");
//			    
//			}else{
//				sb.append(" ");
//			}
//			
//		}catch(Exception e){
//			log.error("[PAGING][Paging.getPaging] - Exception");
//		}
//		
//		return isDataPaging?(sb.toString().replace("goPage", "goDataPage")):sb.toString();
//	}
}
