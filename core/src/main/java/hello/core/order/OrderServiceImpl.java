package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    /**
     * 위의 두 줄은 OrderServiceImpl이 인터페이스 뿐만아니라 실제 클래스에도 의존하고 있는 모습
     * 그래서 할인 정책을 변경하려면 OrderServiceImpl의 코드도 변경해야하는 문제 발생
     * 'DIP위반' -> 'OCP위반'
     * 그래서 코드를 다음과 같이 바꿔준다
     * */
    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
