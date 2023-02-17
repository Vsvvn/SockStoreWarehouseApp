package CourseWork3stCourse.service;

import CourseWork3stCourse.exception.IncorrectParamException;
import CourseWork3stCourse.model.Color;
import CourseWork3stCourse.model.Size;
import CourseWork3stCourse.model.Sock;
import CourseWork3stCourse.model.SockItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class StoreService {

    private final Map<Sock, Integer> socks = new HashMap<>();

    public void income(SockItem sockItem) {
        if (isNotValid(sockItem)) {
            throw new IncorrectParamException();
        }
        Sock sock = sockItem.getSock();
        if (socks.containsKey(sock)) {
            socks.replace(sock, socks.get(sock) + sockItem.getQuantity());
        } else {
            socks.put(sock, sockItem.getQuantity());
        }
    }

    public void expenditure(SockItem sockItem) {
        Sock sock = sockItem.getSock();
        if (!socks.containsKey(sock) || isNotValid(sockItem)) {
            throw new IncorrectParamException();
        }
        int available = socks.get(sock);
        int result = available - sockItem.getQuantity();
        if (result < 0) {
            throw new IncorrectParamException();
        }

        if (socks.containsKey(sock)) {
            socks.replace(sock, socks.get(sock) - sockItem.getQuantity());
        }
    }

    private boolean isNotValid(SockItem sockItem) {
        Sock sock = sockItem.getSock();
        return sock.getCottonPart() < 0 || sock.getCottonPart() > 100 ||
                sockItem.getQuantity() <= 0;
    }

    public int count(String color, float size, int cottonMin, int cottonMax) {

        Color c = Color.parse(color);
        Size s = Size.parse(size);
        if (Objects.isNull(c) || Objects.isNull(s) || cottonMin >= cottonMax || cottonMin < 0 || cottonMax > 100) {
            throw new IncorrectParamException();
        }
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            Sock sock = entry.getKey();
            int available = entry.getValue();
            if (sock.getColor() == c && sock.getSize() == s && sock.getCottonPart() >= cottonMin && sock.getCottonPart() < cottonMax) {
                return available;
            }
        }
        return 0;
    }
}
