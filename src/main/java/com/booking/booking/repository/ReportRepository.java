package com.booking.booking.repository;

import com.booking.booking.dto.FacilityReportDto;

import com.booking.booking.dto.LandlordAllFacilitiesReportDto;
import com.booking.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Reservation, Long> {

    @Query("select f.id as facilityId, f.name as facilityName, sum (r.tenancyPeriodInDays) as totalReservationsDays, " +
            "count (r) as totalReservationsCount from Reservation r join Facility f on r.facility = f " +
            "where f.id = :facilityId and r.startDate >= :starts and r.endDate <= :ends group by f.id")
    FacilityReportDto findAllReservationsDaysCountWithReservationsCount(Long facilityId,
                                                                        LocalDateTime starts,
                                                                        LocalDateTime ends);

    @Query("select l.id as landlordId, l.name as landlordName, count (f) as rentedFacilitiesCount, count (distinct t) as facilityTenantsCount, " +
            "(sum (f.grossPricePerDayInPln) * sum (r.tenancyPeriodInDays)) as totalLandlordIncome "+
            "from Landlord l left join Facility f on l = f.landlord left join Reservation r on f = r.facility " +
            "left join Tenant t on t = r.tenant where r.startDate >= :starts and r.endDate <= :ends " +
            "group by l.id")
    List<LandlordAllFacilitiesReportDto> findReservedFacilitiesCountWithReservationsPerFacilityCountAndFacilitiesIncome(LocalDateTime starts,
                                                                                                                        LocalDateTime ends);

}
