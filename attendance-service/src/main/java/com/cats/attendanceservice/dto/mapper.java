package com.cats.attendanceservice.dto;

import com.cats.attendanceservice.model.LeaveBalance;

import java.util.ArrayList;
import java.util.List;

public class mapper {
//    public static BookResponseDto bookToBookResponseDto(Book book) {
//    BookResponseDto bookResponseDto = new BookResponseDto();
//    bookResponseDto.setId(book.getId());
//    bookResponseDto.setCategoryName(book.getCategory().getName());
//    List<String> names = new ArrayList<>();
//    List<Author> authors = book.getAuthors();
//    for (Author author: authors) {
//        names.add(author.getName());
//    }
//    bookResponseDto.setAuthorNames(names);
//    return bookResponseDto;
//}
//
//    public static List<BookResponseDto> booksToBookResponseDtos(List<Book> books) {
//        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
//        for (Book book: books) {
//            bookResponseDtos.add(bookToBookResponseDto(book));
//        }
//        return bookResponseDtos;
//    }

    public static LeaveBalanceDtoRep leaveBalanceToBookResponseDto(LeaveBalance leaveBalance) {
        LeaveBalanceDtoRep leaveBalanceDtoRep = new LeaveBalanceDtoRep();
        leaveBalanceDtoRep.setId(leaveBalance.getId());
        leaveBalanceDtoRep.setBalanceAmount(leaveBalance.getBalanceAmount());
        leaveBalanceDtoRep.setLeaveType(leaveBalance.getLeaveType().getLeaveTitle());
        leaveBalanceDtoRep.setEmpId(leaveBalance.getEmpId());
        leaveBalanceDtoRep.setLastUpdateDate(leaveBalance.getLastUpdateDate());

        return leaveBalanceDtoRep;
    }
    public static List<LeaveBalanceDtoRep> leaveBalanceToBookResponseDtos(List<LeaveBalance> leaveBalances) {
        List<LeaveBalanceDtoRep> leaveBalanceDtoRep = new ArrayList<>();

        for (LeaveBalance leaveBalance: leaveBalances) {
           leaveBalanceDtoRep.add(leaveBalanceToBookResponseDto(leaveBalance));
        }
        return leaveBalanceDtoRep;
    }
}
