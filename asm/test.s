; ----------------------------------------------------------------------------
; test.s
;
; This is a Win32 console program that writes "Hello, World" on one line and
; then exits.  It needs to be linked with a C library.
; ----------------------------------------------------------------------------

    global  _main
    extern  _printf

    section .text
_main:
    push    message
    call    _printf
    add     esp, 4
    ; Pause the program forever :3
    wait_program:
    nop
    jmp wait_program
message:
    db  'Hello, World', 10, 0