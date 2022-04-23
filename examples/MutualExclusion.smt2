; State function: s (state index, atomic proposition index)
(declare-fun s (Int Int) Bool)

; Transition function: t (source state index, destination state index)
(define-fun t ((x Int) (y Int)) Bool
    (or
        ; 00 -> 01
        (and (not (s x 0)) (not (s x 1)) (not (s y 0)) (s y 1))
        ; 01 -> 00
        (and (not (s x 0)) (s x 1) (not (s y 0)) (not (s y 1)))
        ; 00 -> 10
        (and (not (s x 0)) (not (s x 1)) (s y 0) (not (s y 1)))
        ; 10 -> 00
        (and (s x 0) (not (s x 1)) (not (s y 0)) (not (s y 1)))
        ; 11 -> 00
        (and (s x 0) (s x 1) (not (s y 0)) (not (s y 1)))
        ; Faulty transition: 10 -> 11
        (and (s x 0) (not (s x 1)) (s y 0) (s y 1))
    )
)

; Init: I(s0) = 00
(assert (and (not (s 0 0)) (not (s 0 1))))

; Transition: T(s0, s1)
(assert (t 0 1))

; Transition: T(s1, s2)
(assert (t 1 2))

; Property: G (not (p0 and p1))
; Witness: F (p0 and p1)
(assert (or
    (and (s 0 0) (s 0 1))
    (and (s 1 0) (s 1 1))
    (and (s 2 0) (s 2 1))
))

(check-sat)
(get-value ((s 0 0) (s 0 1)))
(get-value ((s 1 0) (s 1 1)))
(get-value ((s 2 0) (s 2 1)))
