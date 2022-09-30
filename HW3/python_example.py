# A function delcaration begins with 'def' keyword.
def fun_declaration(params):

    # This is the first statement included in 'fun_declaration'
    print("Inside fun_declaration.")

    # All files in this assignment will be a function of some
    # number 'n':
    n = 5

    # Loops use 'iterables' and 'ranges' in Python. This will 
    # help you in the assignment, because it allows us to make loop 
    # ranges more readable. This 'xrange(n)' object will iterate 
    # over values 0 to n-1 in a 'for' block.
    N = xrange(n)

    # A for block begins with the 'for' keyword. In this assignment,
    # 'for' blocks will only ever be one of the following:
    # 
    #     for * in N:
    #     for * in log_N:
    # 
    # where '*' represents some variable name. 
    for i in N:

        # This statement is inside of the 'for' block. It will
        # run 5 times, from i=0 to i=4.
        print("Inside the 'for' block. This is iteration number", i)

        # A while block begins with the 'while' keyword. In this
        # assignment, 'while' loops will always use the following
        # format:
        # 
        #     * = n
        #     while * > 0:
        #
        # where '*' represents some variable name. You must locate
        # the update statement inside the block of code to determine
        # its order of complexity.
        j = n
        while j > 0:

            print("Inside the 'while' block. How many times does "
                  "this loop run? Number", j)

            # This is the update statement. Since j is cut in half
            # every iteration, it will run log_2(n) times.
            j /= 2

            print("Still inside the 'while' block.")

            # End of 'while' block.

        print("Outside of the 'while' block, but still inside the"
              " 'for' block.")

        # End of 'for' block.

    # This statement will execute right after the for loop is
    # exited.
    print("Outside of for loop.")

    # End of 'def' block.

# This statement occurs outside of 'fun_declaration', because it 
# is not indented.
print("Outside of 'fun_declaration'.")
