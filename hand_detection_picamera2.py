from picamera2 import Picamera2
import cv2
import numpy as np

# Inițializează camera
picam2 = Picamera2()
picam2.start()

while True:
    # Capturează un frame ca array NumPy
    frame = picam2.capture_array()

    # Redimensionează pentru performanță
    frame = cv2.resize(frame, (640, 480))

    # Conversie la YCrCb pentru segmentarea pielii
    ycrcb = cv2.cvtColor(frame, cv2.COLOR_BGR2YCrCb)
    lower = np.array([0, 133, 77], dtype=np.uint8)
    upper = np.array([255, 173, 127], dtype=np.uint8)
    mask = cv2.inRange(ycrcb, lower, upper)

    # Filtrare zgomot
    mask = cv2.GaussianBlur(mask, (5, 5), 0)

    # Găsește contururi
    contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    if contours:
        # Ia cel mai mare contur (probabil mâna)
        max_contour = max(contours, key=cv2.contourArea)
        if cv2.contourArea(max_contour) > 1000:
            # Trasează conturul
            cv2.drawContours(frame, [max_contour], -1, (0, 255, 0), 2)
            # Trasează dreptunghiul verde în jurul conturului
            x, y, w, h = cv2.boundingRect(max_contour)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

    # Afișează rezultatul
    cv2.imshow('Hand Detection', frame)
    cv2.imshow('Mask', mask)

    if cv2.waitKey(1) & 0xFF == 27:  # ESC pentru a ieși
        break

cv2.destroyAllWindows() 